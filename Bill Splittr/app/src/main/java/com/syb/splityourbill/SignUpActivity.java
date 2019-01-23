package com.syb.splityourbill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText nameField,signUpEmailField,signUpPassField,signUpCnfPassField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameField =(EditText) findViewById(R.id.nameField);
        signUpCnfPassField =(EditText) findViewById(R.id.signUpCnfPassField);
        signUpEmailField =(EditText) findViewById(R.id.signUpEmailField);
        signUpPassField =(EditText) findViewById(R.id.signUpPassField);

    }

    String name,pass,cnfPass,emailId;

    public void signUp(View v){

        name = nameField.getText().toString();
        pass = signUpPassField.getText().toString();
        cnfPass = signUpCnfPassField.getText().toString();
        emailId = signUpEmailField.getText().toString();


        if(!pass.equals(cnfPass)){                                                //  check for same password
            Toast.makeText(this,"Passwords didn't match.",Toast.LENGTH_SHORT).show();
        }
        else if(name.isEmpty()){
            Toast.makeText(this,"Please enter name.",Toast.LENGTH_SHORT).show();
        }
        else if(emailId.isEmpty()){
            Toast.makeText(this,"Please enter E-mail id.",Toast.LENGTH_SHORT).show();
        }
        else if(pass.isEmpty()){
            Toast.makeText(this,"Please enter password.",Toast.LENGTH_SHORT).show();
        }
        else if(pass.length()<6 || pass.length()>13){
            Toast.makeText(this,"6-13 Charcter Password Required",Toast.LENGTH_SHORT).show();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()){
            Toast.makeText(this,"Invalid E-mail Id.",Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(emailId,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.signInWithEmailAndPassword(emailId,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isComplete()){
                                    User user = new User(name,emailId,pass);
                                    //Uri uri = Uri.parse("android.resource://com.syb.splityourbill/drawable/defprofpic.png");

                                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

                                    mDatabase.child("users").child(FirebaseAuth.getInstance().getUid()).setValue(user);
//                                    FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
//                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                            .setDisplayName(name)
//                                            .setPhotoUri(uri)
//                                            .build();
//
//                                    fuser.updateProfile(profileUpdates);

                                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    SharedPreferences perf = getBaseContext().getSharedPreferences("UserDetail", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = perf.edit();
                                    editor.putString("email",emailId);
                                    editor.putString("name",name);
                                    editor.apply();
                                }
                                else {
                                    Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                                }

                            }
                        });

                    }
                    else{
                        Toast.makeText(SignUpActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

    }
}
