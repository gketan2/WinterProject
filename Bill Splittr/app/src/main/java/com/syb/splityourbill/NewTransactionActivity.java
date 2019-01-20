package com.syb.splityourbill;

//import android.content.Context;
//import android.support.constraint.ConstraintLayout;
//import android.support.constraint.ConstraintSet;
import android.app.Dialog;
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

import java.util.HashMap;
import java.util.LinkedList;

public class NewTransactionActivity extends AppCompatActivity {

    EditText remarks;
    LinkedList<HashMap<String,String>> payeeListData;
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
        payeeListData = new LinkedList<HashMap<String,String>>();
        payeeadapter = new AddPayeeAdapter(this,payeeListData);
        payeeList.setAdapter(payeeadapter);

        //setting participant adapter//
        participantListData = new LinkedList<String>();
        participantadapter = new AddParticipantAdapter(this,participantListData);
        participantList.setAdapter(participantadapter);


        payeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editPayee(view,position);
            }
        });

        participantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editParticipant(view,position);
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
                    HashMap<String,String> map = new HashMap<String,String>();
                    map.put(email,amount);
                    payeeListData.add(map);
                    payeeadapter.notifyDataSetChanged();
                    dialog.dismiss();
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
                    participantListData.add(email);
                    participantadapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else
                    Toast.makeText(NewTransactionActivity.this,"Please Enter Correct Value",Toast.LENGTH_LONG).show();
            }
        });

        dialog.show();

    }

    public void editPayee(View v,final int position){

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
                    payeeListData.set(position,map);
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
                payeeListData.remove(position);
                payeeadapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void editParticipant(View v,final int position){

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
            }
        });

        dialog.show();

    }

    public void split(View v){

    }

}
