package com.dan.ututor.System;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import 	android.content.Intent;
import android.widget.Toast;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dan.ututor.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class LogStudent extends AppCompatActivity {

    //global vars
    private EditText password;
    private EditText email;
    Button login;
    Button reset;
    Button back;
    Button registerstudent;
    Button registertutor;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    private Boolean emailCheck;
    ;

    //start xml
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //find elements in xml
        setContentView(R.layout.activity_logstudent);
        mAuth = FirebaseAuth.getInstance();
        login = (Button) findViewById(R.id.login);
        reset = (Button) findViewById(R.id.reset);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password2);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students");
        databaseReference.keepSynced(true);
        back = (Button) findViewById(R.id.back);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
//method call
                    login();


                }

//onclick direct to correct page
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogStudent.this, SettingsLog.class);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogStudent.this, Log.class);
                startActivity(intent);
                finish();
            }
        });

    }

//check if verified
    private void checkIfEmailVerified()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified())
        {
//redierect to profile

            Intent intent = new Intent(LogStudent.this, StudentProfile.class);
            startActivity(intent);
            finish();
        }
        else
        {

            FirebaseAuth.getInstance().signOut();



        }
    }


//check textfields and sign in
    private void login() {

        String email2 = email.getText().toString();
        String password2 = password.getText().toString();

        if(!TextUtils.isEmpty(email2) && !TextUtils.isEmpty(password2)) {


            mAuth.signInWithEmailAndPassword(email2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {


                        checkUserExist();

                    } else {


                        Toast.makeText(LogStudent.this, "Bad Password", Toast.LENGTH_LONG).show();

                    }

                }
            });}}
// make sue user exists
    private void checkUserExist()
    {

        if(mAuth.getCurrentUser() != null) {
//get user id
            final String user_id = mAuth.getCurrentUser().getUid();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//snapsot of database
                    if (dataSnapshot.hasChild(user_id)) {

                        checkIfEmailVerified();

                    } else {

                        Toast.makeText(LogStudent.this, "Does not exist", Toast.LENGTH_LONG).show();

                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

    }}
