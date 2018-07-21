package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {

    EditText resmail;
    Button resbutt;
    TextView reslogin;
    FirebaseAuth auth;


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }



    private void ResetPassword(String mail)
    {
        if (!TextUtils.isEmpty(resmail.getText().toString().trim()))
        {
            auth.sendPasswordResetEmail(mail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(Forgotpassword.this, "Reset Link has been sent to your Email", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Forgotpassword.this,Login_Activity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Forgotpassword.this, "Reset not successful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            resmail.setError("Required");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        auth = FirebaseAuth.getInstance();

        resmail = findViewById(R.id.resmail);
        reslogin = findViewById(R.id.logintxtvwres);
        resbutt = findViewById(R.id.resbutton);

        resbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetPassword(resmail.getText().toString());
            }
        });
        reslogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Forgotpassword.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
