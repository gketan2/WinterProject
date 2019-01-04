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


public class UserAuthFragment_login extends Fragment {

    public interface SignInInterface {
        public boolean signIn(String emailId,String password);
        public boolean registerUser(String name,String emailId,String pass,String cnfPass);
    }

    SignInInterface linker;

    Button logInButton;
    EditText logInEmailField,logInPassField;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        linker = (SignInInterface) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_auth_login,container,false);

        logInButton = (Button) v.findViewById(R.id.logInButton);
        logInEmailField = (EditText) v.findViewById(R.id.logInEmailField);
        logInPassField = (EditText) v.findViewById(R.id.logInPassField);

        logInButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                logIn(v);
            }
        });

        return v;
    }

    public void logIn(View v){

        String emailid = logInEmailField.getText().toString();
        String password = logInPassField.getText().toString();
        linker.signIn(emailid,password);

    }
}
