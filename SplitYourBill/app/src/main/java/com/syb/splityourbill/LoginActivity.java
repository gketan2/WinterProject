package com.syb.splityourbill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private String email,pass;
    private EditText emailField_login,password;
    private FirebaseAuth auth;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailField_login =(EditText) findViewById(R.id.emailField_login);
        password = (EditText) findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        bar =(ProgressBar) findViewById(R.id.progressBar);

    }

    public void register(View v){
        Intent intent = new Intent(this,register.class);           // start register activity
        startActivity(intent);
    }

    public void login(View v){

        email = emailField_login.getText().toString();
        pass = password.getText().toString();
        if( !email.equals("") && !pass.equals("")){          // firebase code
            bar.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        bar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Logged In Successfully.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            SharedPreferences perf = getBaseContext().getSharedPreferences("UserDetail", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = perf.edit();
                            editor.putString("email",email);
                            editor.commit();
                           finish();

                        }
                        else Toast.makeText(LoginActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
        }
        else{                                  // phone or pass is not null CHECK
            Toast.makeText(this,"Please Enter valid E-mail and password",Toast.LENGTH_SHORT).show();
            emailField_login.requestFocus();
        }

    }
}
