package com.syb.splityourbill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LogInActivity extends AppCompatActivity {


    EditText logInEmailField,logInPassField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        logInEmailField = (EditText)findViewById(R.id.logInEmailField);
        logInPassField = (EditText) findViewById(R.id.logInPassField);
    }

    String emailId,password;
    public void logIn(View v){

        emailId = logInEmailField.getText().toString();
        password = logInPassField.getText().toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if( !emailId.equals("") && !password.equals("")){          // firebase code

            auth.signInWithEmailAndPassword(emailId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                        finish();
                        startActivity(intent);
                        SharedPreferences perf = getBaseContext().getSharedPreferences("UserDetail", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = perf.edit();
                        editor.putString("email",emailId);
                        editor.putString("name",FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                        editor.apply();


                    }
                    else {
                        Toast.makeText(LogInActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }

                }

            });
        }
        else{                                  // email or pass is not null CHECK
            Toast.makeText(this,"Please Enter valid E-mail and password",Toast.LENGTH_SHORT).show();
        }

    }

    public void forgotPassword(View v){
        Intent intent = new Intent(this,ForgetPasswordActivity.class);
        startActivity(intent);
    }

    public void signUpActivity(View v){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }
}
