package com.syb.splityourbill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    String phone,pass;
    EditText phonenumber,password;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phonenumber =(EditText) findViewById(R.id.phonenumber);
        password = (EditText) findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();

    }

    public void register(View v){
        Intent intent = new Intent(this,register.class);           // start register activity
        startActivity(intent);
    }

    public void login(View v){
        phone = phonenumber.getText().toString();
        pass = password.getText().toString();
        if( !phone.equals("") && !pass.equals("")){          // firebase code

        }
        else{                                  // phone or pass is not null CHECK
            Toast.makeText(this,"Please Enter valid phone or password",Toast.LENGTH_SHORT).show();
            phonenumber.requestFocus();
        }

    }
}
