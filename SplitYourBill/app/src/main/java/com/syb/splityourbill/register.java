package com.syb.splityourbill;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class register extends AppCompatActivity {

    EditText nameField,emailField,passField,cnfPassField;
    Button signUpButton;
    private String name,email,pass,cnfpass;
    private FirebaseAuth Auth;

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

    }

    public void signUp(View v){
        name = nameField.getText().toString();
        email = emailField.getText().toString();
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
        else{                // do firebase code
                Auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(register.this,"Registered Successfully. Login here.",Toast.LENGTH_SHORT).show();
                            finish();
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
