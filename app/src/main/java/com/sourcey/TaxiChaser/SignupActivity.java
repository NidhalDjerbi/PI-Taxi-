package com.sourcey.TaxiChaser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    String uuid;
    String userFunction="Client";
    private  String username;
    private static final String TAG = "SignupActivity";
    private DatabaseReference mDatabase;
    @Bind(R.id.input_name) EditText _nameText;
    @Bind(R.id.input_address) EditText _addressText;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_mobile) EditText _mobileText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @Bind(R.id.radio_Client) RadioButton _radioButton1;
    @Bind(R.id.radio_Chauffeur) RadioButton _radioButton2;
    @Bind(R.id.radio_Proprietaire) RadioButton _radioButton3;
    @Bind(R.id.UserFunction)  RadioGroup _radioGroup;

    @Bind(R.id.btn_signup) Button _signupButton;
    @Bind(R.id.link_login) TextView _loginLink;

    User user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

     _radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {

             if(checkedId==_radioButton1.getId()){
                 userFunction="Client";
             }else  if (checkedId==_radioButton2.getId()){
                 userFunction="Chauffeur";
             }else userFunction="Proprietaire";


         }
     });


        mDatabase = FirebaseDatabase.getInstance().getReference();

        _nameText= (EditText) findViewById(R.id.input_name);
        _addressText= (EditText) findViewById(R.id.input_address);
        _emailText= (EditText) findViewById(R.id.input_email);
        _mobileText= (EditText) findViewById(R.id.input_mobile);
        _passwordText= (EditText) findViewById(R.id.input_password);
        _reEnterPasswordText= (EditText) findViewById(R.id.input_reEnterPassword);
        _radioButton1= (RadioButton) findViewById(R.id.radio_Client);
        _radioButton2= (RadioButton) findViewById(R.id.radio_Chauffeur);
        _radioButton3= (RadioButton) findViewById(R.id.radio_Proprietaire);

        _radioGroup= (RadioGroup) findViewById(R.id.UserFunction);
        _signupButton= (Button) findViewById(R.id.btn_signup);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=_nameText.getText().toString();
                String address=_addressText.getText().toString();
                final String email=_emailText.getText().toString();
                String mobile=_mobileText.getText().toString();
                String passWord=_passwordText.getText().toString();
                String radioButton1=_radioButton1.getText().toString();
                String radioButton2=_radioButton2.getText().toString();
                String radioButton3=_radioButton3.getText().toString();

                // Write a message to the database
                mDatabase.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        boolean finished=true;
                        exit:

                        for (DataSnapshot childDataSnapshot : snapshot.getChildren()) {
                            Log.d("key",childDataSnapshot.getKey());
                                if(email.equals(childDataSnapshot.child("email").getValue().toString())&&childDataSnapshot.getKey()!=uuid){
                                    //System.out.print("--------------------existtt");


                                    mDatabase.child("users").child(uuid).removeValue();
                                    finished=false;
                                    break exit;
                                }
                        }

                        if(finished){
                            signup();
                        }else
                        {
                            _emailText.setError("Email Exist");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

               /* mDatabase.child("users").child("username").push().setValue(name);
                mDatabase.child("users").child("address").push().setValue(address);
                mDatabase.child("users").child("email").push().setValue(email);
                mDatabase.child("users").child("mobile").push().setValue(mobile);
                mDatabase.child("users").child("passWord").push().setValue(passWord);
                if (_radioButton1.isChecked()){
                    mDatabase.child("users").child("Function").push().setValue(radioButton1);
                }else if (_radioButton2.isChecked()){
                    mDatabase.child("users").child("Function").push().setValue(radioButton2);
                }else mDatabase.child("users").child("Function").push().setValue(radioButton3);*/

            if (validate()){
                user=new User(name,address, userFunction,email,mobile,passWord) ;


                     uuid = UUID.randomUUID().toString();
                    mDatabase.child("users").child(uuid).setValue(user);
                User.userId=uuid;


            }



            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();




        Intent maps= new Intent(this,Drawer.class);
        User.Username=_nameText.getText().toString();
        maps.putExtra("currentUser", user);
        startActivity(maps);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "signup failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=8) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_Client:
                if (checked)

                    break;
            case R.id.radio_Proprietaire:
                if (checked)

                    break;
            case R.id.radio_Chauffeur:
                if (checked)

                    break;
        }
    }
}