package com.syb.splityourbill;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class UserAuthFragment_signup extends Fragment {

    UserAuthFragment_login.SignInInterface linker;

    Button signUpButton;
    EditText signUpEmailField,signUpPassField,signUpCnfPassField,nameField;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        linker = (UserAuthFragment_login.SignInInterface) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_auth_signup,container,false);

        signUpButton = (Button) v.findViewById(R.id.signUpButton);
        signUpEmailField = (EditText) v.findViewById(R.id.signUpEmailField);
        signUpPassField = (EditText) v.findViewById(R.id.signUpPassField);
        signUpCnfPassField = (EditText) v.findViewById(R.id.signUpCnfPassField);
        nameField = (EditText) v.findViewById(R.id.nameField);

        signUpButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                signUp();
            }
        });


        return v;
    }

    public void signUp(){
        String name = nameField.getText().toString();
        String emailid = signUpEmailField.getText().toString();
        String pass = signUpPassField.getText().toString();
        String cnfpass = signUpCnfPassField.getText().toString();


        boolean b = linker.registerUser(name,emailid,pass,cnfpass);
        if(b){
            linker.signIn(emailid,pass);
        }

    }
}
