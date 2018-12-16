package com.syb.splityourbill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {

    private EditText nameField,emailField,passField,cnfPassField;
    private Button signUpButton;
    private String name,email,pass,cnfpass;
    private FirebaseAuth Auth;
    ProgressBar bar;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameField = (EditText)findViewById(R.id.nameField);
        emailField = (EditText)findViewById(R.id.emailField);
        passField = (EditText)findViewById(R.id.passField);
        cnfPassField = (EditText)findViewById(R.id.cnfPassField);
        signUpButton = (Button)findViewById(R.id.signUpButton);
        Auth = FirebaseAuth.getInstance();
        bar = findViewById(R.id.progressBar2);
        mDatabase = FirebaseDatabase.getInstance().getReference();

    }


    public void signUp(View v){
        name = nameField.getText().toString().trim();
        email = emailField.getText().toString().trim();
        pass = passField.getText().toString();
        cnfpass = cnfPassField.getText().toString();

        if(!pass.equals(cnfpass)){                                                //  check for same password
            Toast.makeText(this,"Passwords didn't match.",Toast.LENGTH_SHORT).show();
            passField.setText("");
            cnfPassField.setText("");
            passField.requestFocus();
        }
        else if(name.isEmpty()){
            Toast.makeText(register.this,"Please enter name.",Toast.LENGTH_SHORT).show();
        }
        else if(email.isEmpty()){
            Toast.makeText(register.this,"Please enter E-mail id.",Toast.LENGTH_SHORT).show();
        }
        else if(pass.isEmpty()){
            Toast.makeText(register.this,"Please enter password.",Toast.LENGTH_SHORT).show();
        }
        else if(pass.length()<6){
            Toast.makeText(register.this,"Minimum 6 Charcter Password Required",Toast.LENGTH_SHORT).show();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(register.this,"Invalid E-mail Id.",Toast.LENGTH_SHORT).show();
        }
        else{                // do firebase code

            bar.setVisibility(View.VISIBLE);
                Auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        bar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"Registered Successfully.",Toast.LENGTH_SHORT).show();
                            bar.setVisibility(View.VISIBLE);
                            Auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {


                                    bar.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                        String userId = FirebaseAuth.getInstance().getUid();
                                        User user = new User(name,email);
                                        mDatabase.child("users").child(userId).setValue(user);


                                        Toast.makeText(register.this,"Logged In Successfully.",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(register.this, HomeActivity.class);

                                        SharedPreferences pref = getBaseContext().getSharedPreferences("UserDetail", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("email",email);
                                        editor.putString("name",name);
                                        editor.apply();

                                        intent.putExtra("name",name);
                                        intent.putExtra("email",email);
                                        intent.putExtra("userId",userId);
                                        startActivity(intent);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);


                                    }
                                    else Toast.makeText(register.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                        else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(register.this,"User already registered. Login here.",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            Toast.makeText(register.this,"Could not register. Please try again.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }
    }
}
