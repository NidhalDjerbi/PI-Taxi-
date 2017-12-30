package com.sourcey.TaxiChaser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private DatabaseReference mDatabase;
    private  String username;
    boolean validateUser=false;
    boolean finishedLoop=false;
    User user;
    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");
        Log.d("password",_passwordText.getText().toString());
        Log.d("email",_emailText.getText().toString());
        if (!validate()) {
            onLoginFailed();
            return;
        }else {
            CheckUser();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();





        Intent maps= new Intent(this,Drawer.class);

        Log.d("username",username);
        User.Username=username;
        maps.putExtra("currentUser",user);
        startActivity(maps);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }


    private void CheckUser() {
        validateUser=false;

        mDatabase.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // do some stuff once
                 for (DataSnapshot childDataSnapshot : snapshot.getChildren()){
                     Log.d("email",childDataSnapshot.child("email").getValue().toString());
                     Log.d("password",childDataSnapshot.child("password").getValue().toString());
                     if(_emailText.getText().toString().equals(childDataSnapshot.child("email").getValue().toString())){
                        if(_passwordText.getText().toString().equals(childDataSnapshot.child("password").getValue().toString())){
                                validateUser=true;
                                user=new User(childDataSnapshot.child("username").getValue().toString(),childDataSnapshot.child("address").getValue().toString(),childDataSnapshot.child("function").getValue().toString(),childDataSnapshot.child("email").getValue().toString(),childDataSnapshot.child("mobile").getValue().toString(),childDataSnapshot.child("password").getValue().toString());
                                username=childDataSnapshot.child("username").getValue().toString();
                                User.userId=childDataSnapshot.getKey().toString();
                            _loginButton.setEnabled(false);

                            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                                    R.style.AppTheme_Dark_Dialog);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Authenticating...");
                            progressDialog.show();

                            String email = _emailText.getText().toString();
                            String password = _passwordText.getText().toString();

                            // TODO: Implement your own authentication logic here.

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onLoginSuccess or onLoginFailed
                                            onLoginSuccess();
                                            // onLoginFailed();
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);



                         }



                     }
                 }

                if(!validateUser)
                onLoginFailed();




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

    }

    public boolean callback() {
        // do your stuff here
        return validateUser;
    }
    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }




        return valid;
    }
}
