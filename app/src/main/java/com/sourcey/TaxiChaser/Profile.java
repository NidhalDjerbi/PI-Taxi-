package com.sourcey.TaxiChaser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;

public class Profile extends AppCompatActivity {
Button UpdateUser;


    private DatabaseReference mDatabase;

    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_address) EditText _addressText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_mobile) EditText _mobileText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UpdateUser =(Button)findViewById(R.id.btn_update);


        Intent intent=getIntent();
        User user=(User)intent.getSerializableExtra("currentUser");

        mDatabase = FirebaseDatabase.getInstance().getReference();




        _nameText= (EditText) findViewById(R.id.input_name);
        _addressText= (EditText) findViewById(R.id.input_address);
        _emailText= (EditText) findViewById(R.id.input_email);
        _mobileText= (EditText) findViewById(R.id.input_mobile);
        _passwordText= (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText= (EditText) findViewById(R.id.input_reEnterPassword);

        _nameText.setHint(user.username);
        _addressText.setHint(user.address);
        _emailText.setHint(user.email);
        _mobileText.setHint(user.mobile);


        UpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               if(! _nameText.getText().toString().equals("")){
                   User.Username=_nameText.getText().toString();
                mDatabase.child("users").child(User.userId).child("username").setValue(_nameText.getText().toString());
               }
                if(! _addressText.getText().toString().equals(""))
                    mDatabase.child("users").child(User.userId).child("address").setValue(_addressText.getText().toString());
                if( !_emailText.getText().toString().equals(""))
                    mDatabase.child("users").child(User.userId).child("email").setValue(_emailText.getText().toString());
                if(! _mobileText.getText().toString().equals(""))
                    mDatabase.child("users").child(User.userId).child("mobile").setValue(_mobileText.getText().toString());



                if(_passwordText.getText().toString()!=null&&_reEnterPasswordText.getText().toString()!=null&&_passwordText.getText().toString()==_reEnterPasswordText.getText().toString()){
                    mDatabase.child("users").child(User.userId).child("password").setValue(_passwordText.getText().toString());

                }

            }
        });

    }
}
