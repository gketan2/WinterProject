package com.syb.splityourbill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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
            Toast.makeText(this,"Enter correct password",Toast.LENGTH_SHORT).show();
            passField.setText("");
            cnfPassField.setText("");
            passField.requestFocus();
        }
        else{                // do firebase code
                Auth.createUserWithEmailAndPassword(email,pass);
        }
    }
}
