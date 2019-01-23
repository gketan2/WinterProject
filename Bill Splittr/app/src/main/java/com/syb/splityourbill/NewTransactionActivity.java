package com.syb.splityourbill;

//import android.content.Context;
//import android.support.constraint.ConstraintLayout;
//import android.support.constraint.ConstraintSet;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.Display;
import android.view.View;
//import android.view.WindowManager;
//import android.widget.Button;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedList;

public class NewTransactionActivity extends AppCompatActivity {

    EditText remarks;
    LinkedList<String> payeeData;
    LinkedList<Integer> payeeAmountData;
    LinkedList<String> participantListData;
    ListView payeeList,participantList;
    AddPayeeAdapter payeeadapter;
    AddParticipantAdapter participantadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_new_transaction);
        remarks =(EditText) findViewById(R.id.remarks);
        payeeList =(ListView)findViewById(R.id.listviewpayee);
        participantList =(ListView)findViewById(R.id.listviewparticipant);

        //setting payee adapter//
        payeeData = new LinkedList<String>();
        payeeAmountData = new LinkedList<Integer>();
        payeeadapter = new AddPayeeAdapter(this,payeeData,payeeAmountData);
        payeeList.setAdapter(payeeadapter);

        //setting participant adapter//
        participantListData = new LinkedList<String>();
        participantadapter = new AddParticipantAdapter(this,participantListData);
        participantList.setAdapter(participantadapter);


        payeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editPayee(position);
            }
        });

        participantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editParticipant(position);
            }
        });
    }



    public void addNewPayee(View v){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.payeedataentrydialog);

        final EditText dialogInputEmail = (EditText) dialog.findViewById(R.id.dialogInputEmail);
        final EditText dialogInputAmount = (EditText) dialog.findViewById(R.id.dialogInputAmount);
        Button dialoginputOkButton = (Button) dialog.findViewById(R.id.dialoginputOkButton);
        Button dialoginputCancelButton = (Button) dialog.findViewById(R.id.dialoginputCancelButton);


        dialoginputCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialoginputOkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = dialogInputEmail.getText().toString();
                String amount = dialogInputAmount.getText().toString();
                if(!email.isEmpty() && !amount.isEmpty()){
                    if(!payeeData.contains(email) && !participantListData.contains(email)){
                        payeeData.add(email);
                        payeeAmountData.add(Integer.parseInt(amount));
                        payeeadapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(NewTransactionActivity.this,"Already Exist",Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(NewTransactionActivity.this,"Please Enter Correct Value",Toast.LENGTH_LONG).show();
            }
        });

        dialog.show();
    }

    public void addNewParticipant(View v){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.payeedataentrydialog);

        final EditText dialogInputEmail = (EditText) dialog.findViewById(R.id.dialogInputEmail);
        final EditText dialogInputAmount = (EditText) dialog.findViewById(R.id.dialogInputAmount);
        Button dialoginputOkButton = (Button) dialog.findViewById(R.id.dialoginputOkButton);
        Button dialoginputCancelButton = (Button) dialog.findViewById(R.id.dialoginputCancelButton);


        dialogInputAmount.setVisibility(View.GONE);


        dialoginputCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialoginputOkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = dialogInputEmail.getText().toString();
                if(!email.isEmpty()){
                    if(!payeeData.contains(email) && !participantListData.contains(email)){
                        participantListData.add(email);
                        participantadapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(NewTransactionActivity.this,"ALready Exist",Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(NewTransactionActivity.this,"Please Enter Correct Value",Toast.LENGTH_LONG).show();
            }
        });

        dialog.show();

    }

    public void editPayee(final int position){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.payeedataentrydialog);

        final EditText dialogInputEmail = (EditText) dialog.findViewById(R.id.dialogInputEmail);
        final EditText dialogInputAmount = (EditText) dialog.findViewById(R.id.dialogInputAmount);
        Button dialoginputOkButton = (Button) dialog.findViewById(R.id.dialoginputOkButton);
        Button dialoginputCancelButton = (Button) dialog.findViewById(R.id.dialoginputCancelButton);
        Button deleteButton = (Button) dialog.findViewById(R.id.deleteButton);


        HashMap<String,Integer> map = payeeadapter.getItem(position);
        String x = map.keySet().toArray()[0].toString();
        String y = map.get(x).toString();
        dialogInputEmail.setText(x);
        dialogInputAmount.setText(y);

        deleteButton.setVisibility(View.VISIBLE);


        dialoginputCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialoginputOkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = dialogInputEmail.getText().toString();
                String amount = dialogInputAmount.getText().toString();
                if(!email.isEmpty() && !amount.isEmpty()){
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put(email,amount);
                    payeeData.set(position,email);
                    payeeAmountData.set(position,Integer.parseInt(amount));
                    payeeadapter.notifyDataSetChanged();
                    dialog.dismiss();

                }
                else
                    Toast.makeText(NewTransactionActivity.this,"Please Enter Correct Value",Toast.LENGTH_LONG).show();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payeeData.remove(position);
                payeeAmountData.remove(position);
                payeeadapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void editParticipant(final int position){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.payeedataentrydialog);

        final EditText dialogInputEmail = (EditText) dialog.findViewById(R.id.dialogInputEmail);
        final EditText dialogInputAmount = (EditText) dialog.findViewById(R.id.dialogInputAmount);
        Button dialoginputOkButton = (Button) dialog.findViewById(R.id.dialoginputOkButton);
        Button dialoginputCancelButton = (Button) dialog.findViewById(R.id.dialoginputCancelButton);
        Button deleteButton = (Button) dialog.findViewById(R.id.deleteButton);


        String email = participantListData.get(position);
        dialogInputEmail.setText(email);


        dialogInputAmount.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.VISIBLE);


        dialoginputCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialoginputOkButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = dialogInputEmail.getText().toString();
                if(!email.isEmpty()){
                    participantListData.set(position,email);
                    participantadapter.notifyDataSetChanged();
                    dialog.dismiss();

                }
                else
                    Toast.makeText(NewTransactionActivity.this,"Please Enter Correct Value",Toast.LENGTH_LONG).show();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                participantListData.remove(position);
                participantadapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void split(View v){

        try{
            HashMap<String,Integer> data = new HashMap<String,Integer>();
            for(int i=0;i<payeeData.size();i++){
                data.put(payeeData.get(i),payeeAmountData.get(i));
            }
            for(String x :participantListData){
             data.put(x,0);
            }
            //NewTransaction data = new NewTransaction(payeeData,payeeAmountData,participantListData);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("transaction");
            mDatabase.push().setValue(data);
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
            finish();
        }catch(Exception e){
            Toast.makeText(this,"Something Went Wrong\nCheck Internet Connection.",Toast.LENGTH_LONG);
        }

    }

}
