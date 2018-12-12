package com.syb.splityourbill;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private String email,pass;
    private EditText emailField_login,password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailField_login =(EditText) findViewById(R.id.emailField_login);
        password = (EditText) findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

    }

    public void register(View v){
        Intent intent = new Intent(this,register.class);           // start register activity
        startActivity(intent);
    }

    public void login(View v){
        email = emailField_login.getText().toString();
        pass = password.getText().toString();
        if( !email.equals("") && !pass.equals("")){          // firebase code
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Logged In Successfully.",Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(LoginActivity.this, register.class);
                            //startActivity(intent);

                        }
                        else Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        }
        else{                                  // phone or pass is not null CHECK
            Toast.makeText(this,"Please Enter valid phone or password",Toast.LENGTH_SHORT).show();
            emailField_login.requestFocus();
        }

    }
}
