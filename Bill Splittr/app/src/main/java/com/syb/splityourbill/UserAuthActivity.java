package com.syb.splityourbill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserAuthActivity extends AppCompatActivity implements UserAuthFragment_login.SignInInterface {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_auth);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_auth, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position){
                case 0:
                    UserAuthFragment_login loginfrag = new UserAuthFragment_login();
                    return loginfrag;
                case 1:
                    UserAuthFragment_signup signinfrag = new UserAuthFragment_signup();
                    return signinfrag;
                default:
                    UserAuthFragment_login loginfrag1 = new UserAuthFragment_login();
                    return loginfrag1;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    }


    /// INTERFACE METHODS  /////


    @Override
    public void registerUser(final String name , final String emailId,final String pass,final String cnfPass) {

        status =0;

        if(!pass.equals(cnfPass)){                                                //  check for same password
            Toast.makeText(this,"Passwords didn't match.",Toast.LENGTH_SHORT).show();
            status =  1;
        }
        else if(name.isEmpty()){
            Toast.makeText(UserAuthActivity.this,"Please enter name.",Toast.LENGTH_SHORT).show();
            status =  1;
        }
        else if(emailId.isEmpty()){
            Toast.makeText(UserAuthActivity.this,"Please enter E-mail id.",Toast.LENGTH_SHORT).show();
            status =  1;
        }
        else if(pass.isEmpty()){
            Toast.makeText(UserAuthActivity.this,"Please enter password.",Toast.LENGTH_SHORT).show();
            status =  1;
        }
        else if(pass.length()<6){
            Toast.makeText(UserAuthActivity.this,"Minimum 6 Charcter Password Required",Toast.LENGTH_SHORT).show();
            status =  1;
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches()){
            Toast.makeText(UserAuthActivity.this,"Invalid E-mail Id.",Toast.LENGTH_SHORT).show();
            status =  1;
        }
        else{
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(emailId,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseAuth auth = FirebaseAuth.getInstance();
                        auth.signInWithEmailAndPassword(emailId,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isComplete()){
                                User user = new User(name,emailId,pass);

                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                mDatabase.child("users").child(FirebaseAuth.getInstance().getUid()).setValue(user);

                                Intent intent = new Intent(UserAuthActivity.this, HomeActivity.class);
                                finish();
                                startActivity(intent);
                                SharedPreferences perf = getBaseContext().getSharedPreferences("UserDetail", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = perf.edit();
                                editor.putString("email",emailId);
                                editor.putString("name",name);
                                editor.apply();
                                }
                                else {
                                    Toast.makeText(UserAuthActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                                    status = 2;
                                }

                            }
                        });

                        status = 0;
                    }
                    else{
                        Toast.makeText(UserAuthActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        status = 2;
                    }
                }
            });
        }
        if(status==2)
            Toast.makeText(UserAuthActivity.this,"Something Went wrong",Toast.LENGTH_SHORT).show();
    }

    int status = 0;
    String name="";

    @Override
    public void signIn(final String emailId, String password) {

        status = 0;
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if( !emailId.equals("") && !password.equals("")){          // firebase code

            auth.signInWithEmailAndPassword(emailId,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(UserAuthActivity.this, HomeActivity.class);
                        finish();
                        startActivity(intent);
                        SharedPreferences perf = getBaseContext().getSharedPreferences("UserDetail", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = perf.edit();
                        editor.putString("email",emailId);
                        editor.putString("name",name);
                        editor.apply();


                    }
                    else {
                        Toast.makeText(UserAuthActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        status = 1;
                    }

                }

            });
        }
        else{                                  // email or pass is not null CHECK
            Toast.makeText(this,"Please Enter valid E-mail and password",Toast.LENGTH_SHORT).show();
            status =  1;
        }

    }


    @Override
    public void forgetPassword(){
        Intent intent = new Intent(this,ForgetPasswordActivity.class);
        startActivity(intent);
    }
}
