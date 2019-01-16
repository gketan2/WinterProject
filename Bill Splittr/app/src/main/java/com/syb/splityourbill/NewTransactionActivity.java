package com.syb.splityourbill;

//import android.content.Context;
//import android.support.constraint.ConstraintLayout;
//import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.Display;
import android.view.View;
//import android.view.WindowManager;
//import android.widget.Button;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.LinkedList;

public class NewTransactionActivity extends AppCompatActivity {

    EditText remarks;//payee1,amount1;
    LinkedList<String> payeeListData;// = new LinkedList<String>();
    ListView payeeList,participantList;
    AddPayeeAdapter payeeadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);
        remarks =(EditText) findViewById(R.id.remarks);
        payeeList =(ListView)findViewById(R.id.listviewparticipant);
        participantList =(ListView)findViewById(R.id.listviewpayee);
//        payee1 =(EditText) findViewById(R.id.payee1);
//        amount1 = (EditText) findViewById(R.id.amount1);
//        idList.add(R.id.payee1);
//        idList.add(R.id.amount1);
        payeeListData = new LinkedList<String>();
        payeeListData.add("");payeeListData.add("");
        payeeadapter = new AddPayeeAdapter(this,payeeListData);
        payeeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                editPayee(view);
            }
        });

        participantList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                editParticipant(view);
            }
        });
    }



    public void addNewPayee(View v){}

    public void addNewParticipant(View v){}

    public void editPayee(View v){}

    public void editParticipant(View v){}


    //public void addPayee(View v){



//        ConstraintLayout cL = (ConstraintLayout)findViewById(R.id.mainLayout);
//        Display display = ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//
//        EditText payee = new EditText(this);
//        int x = payee.generateViewId();
//        idList.add(x);
//        payee.setId(x);
//        payee.setEms(10);
//        payee.setHint("Email");
//        EditText amount = new EditText(this);
//        int y = amount.generateViewId();
//        idList.add(y);
//        amount.setId(y);
//        amount.setEms(5);
//        amount.setHint("Amount");
//
//        cL.addView((EditText)findViewById(x));
//
//        ConstraintSet constraintset = new ConstraintSet();
//        constraintset.clone(cL);
//        constraintset.connect(x,ConstraintSet.TOP,idList.get(idList.size()-2), ConstraintSet.BOTTOM, 16);
//        constraintset.applyTo(cL);

//        ConstraintLayout.LayoutParams payee_params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
//        payee_params.topToBottom = idList.get(idList.size()-2);
//        payee_params.leftToLeft = R.id.mainLayout;
//        payee_params.leftMargin = 16;
//        payee_params.topMargin = 13;
//        cL.addView(payee,payee_params);

//        ConstraintLayout.LayoutParams amount_params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
//        amount_params.topToBottom = idList.get(idList.size()-1);
//        amount_params.rightToRight = R.id.mainLayout;
//        amount_params.rightMargin = 16;
//        amount_params.topMargin = 13;
//        cL.addView(amount,amount_params);
//
//        ConstraintLayout.LayoutParams addButton_params = new ConstraintLayout.LayoutParams(50,50);
//        addButton_params.topToBottom = idList.get(idList.size()-1);
//        addButton_params.rightToRight = R.id.mainLayout;
//        ((Button)findViewById(R.id.addPayeeButton)).setLayoutParams(addButton_params);


    //}
}
