package com.syb.splityourbill;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    EditText nameField,phoneField,passField,cnfPassField;
    Button signUpButton;
    String name,phone,pass,cnfpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameField = (EditText)findViewById(R.id.nameField);
        phoneField = (EditText)findViewById(R.id.phoneField);
        passField = (EditText)findViewById(R.id.passField);
        cnfPassField = (EditText)findViewById(R.id.cnfPassField);
        signUpButton = (Button)findViewById(R.id.signUpButton);

    }

    public void signUp(View v){
        name = nameField.getText().toString();
        phone = phoneField.getText().toString();
        pass = passField.getText().toString();
        cnfpass = cnfPassField.getText().toString();

        if(!pass.equals(cnfpass)){                                                //  check for same password
            Toast.makeText(this,"Enter correct password",Toast.LENGTH_SHORT).show();
            passField.setText("");
            cnfPassField.setText("");
            passField.requestFocus();
        }
        else{                // do firebase code

        }
    }
}
