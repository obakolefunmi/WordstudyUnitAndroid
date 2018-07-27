package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

import static com.cuwordstudy.solomolaiye.wordstudyunit.MainActivity.curruser;

public class changepassword extends AppCompatActivity {
    EditText editpass;
    Button Changebutton;
    ProgressBar Changepgb;
    private FirebaseAuth auth;


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);
        auth = MainActivity.auth;

        editpass = findViewById(R.id.changepasswordedit);
        Changebutton = findViewById(R.id.changeButton);
        Changepgb = findViewById(R.id.changepgb);

        Changebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editpass.getText().toString().trim()))
                {
                    editpass.setError("Required");
                }
                else
                {
                    editpass.setVisibility(View.GONE);
                    Changebutton.setVisibility(View.GONE);
                    Changepgb.setVisibility(View.VISIBLE);
                    String newpassword = editpass.getText().toString();

                    curruser.updatePassword(newpassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            editpass.setVisibility(View.VISIBLE);
                            Changebutton.setVisibility(View.VISIBLE);
                            Changepgb.setVisibility(View.GONE);
                            Intent intent = new Intent(changepassword.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            editpass.setVisibility(View.VISIBLE);
                            Changebutton.setVisibility(View.VISIBLE);
                            Changepgb.setVisibility(View.GONE);
                            Toast.makeText(changepassword.this, "Sorry failed to change your password "+e, Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }
        });
    }



}
