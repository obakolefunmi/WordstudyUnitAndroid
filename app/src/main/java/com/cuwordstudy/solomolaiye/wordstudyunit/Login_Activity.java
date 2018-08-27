package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login_Activity extends AppCompatActivity {
    ProgressBar pgblogin;
    TextInputLayout emaillay, passlay;
    Button loginbutt;
    EditText loginmail, loginpass;
    TextView forgotpass, signup;
    ConnectivityManager connectivityManager;

    public static FirebaseApp app;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        loginbutt = findViewById(R.id.loginbutt);
        loginmail = findViewById(R.id.loginmail);
        loginpass = findViewById(R.id.loginpass);
        forgotpass = findViewById(R.id.forgotpass);
        signup = findViewById(R.id.signuptxtvw);
        pgblogin = findViewById(R.id.pgblogin);
        emaillay = findViewById(R.id.textinputlayout2);
        passlay = findViewById(R.id.textinputlayout1);
        loginbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser(loginmail.getText().toString(), loginpass.getText().toString());

            }
        });
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Login_Activity.this,Forgotpassword.class);
                startActivity(intent);
                finish();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login_Activity.this,Signup.class);
                startActivity(intent);
                finish();
            }
        });
        connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

    }



    private void LoginUser(String mail, String password)
    {

        if (TextUtils.isEmpty(mail.toString().trim() ))
        {
            loginmail.setError("Required");
            loginmail.requestFocus();

        }
        else if (!mail.toString().contains("@"))
        {
            loginmail.setError("Email Not Valid");
            loginmail.requestFocus();
        }
        else if (TextUtils.isEmpty(password.toString().trim()))
        {
            loginpass.setError("Required");
            loginpass.requestFocus();
        }
        else
        {
            pgblogin.setVisibility(View.VISIBLE);
            loginbutt.setVisibility(View.GONE);
            loginmail.setVisibility(View.GONE);
            loginpass.setVisibility (View.GONE);
            forgotpass.setVisibility(View.GONE) ;
            emaillay.setVisibility(View.GONE) ;
            passlay.setVisibility(View.GONE);
            signup.setVisibility(View.GONE);
            auth.signInWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(Login_Activity.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                pgblogin.setVisibility(View.GONE);
                                loginbutt.setVisibility(View.VISIBLE);
                                loginmail.setVisibility(View.VISIBLE) ;
                                loginpass.setVisibility(View.VISIBLE) ;
                                forgotpass.setVisibility(View.VISIBLE);
                                signup.setVisibility(View.VISIBLE);
                                emaillay.setVisibility(View.VISIBLE);
                                passlay.setVisibility(View.VISIBLE);
                                Intent gotomain = new Intent(Login_Activity.this, MainActivity.class);
                                gotomain.putExtra("email", loginmail.getText());
                                startActivity(gotomain);
                                finish();

                            }
                            else
                            {
                                pgblogin.setVisibility(View.GONE);
                                loginbutt.setVisibility(View.VISIBLE);
                                loginmail.setVisibility(View.VISIBLE) ;
                                loginpass.setVisibility(View.VISIBLE);
                                forgotpass.setVisibility(View.VISIBLE);
                                signup.setVisibility(View.VISIBLE) ;
                                emaillay.setVisibility(View.VISIBLE);
                                passlay.setVisibility(View.VISIBLE) ;
                                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                                try
                                {
                                    boolean isOnline = networkInfo.isConnected();

                                    if (isOnline == true)
                                    {
                                        Toast.makeText(Login_Activity.this, "Invalid Email or password!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(Login_Activity.this, "Connect to the internet and try again!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(Login_Activity.this, "Connect to the internet and try again!", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }
                    });
        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser()!= null)
        {
            String m = auth.getCurrentUser().getEmail().toString();
            Intent gotomain = new Intent(Login_Activity.this, MainActivity.class);
            startActivity(gotomain);
            finish();
        }
    }




}
