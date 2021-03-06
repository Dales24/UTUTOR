package com.dan.ututor.System;

import android.content.Intent;
import android.os.Bundle;


import com.dan.ututor.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import android.text.TextUtils;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.AuthResult;
public class StudentReg extends AppCompatActivity {

//global vars

    //    Person person = new Person();

    private FirebaseAuth mAuth;
    private EditText school;
    private EditText age;
    private EditText name;
    private EditText location;
    private EditText description;
    private EditText gpa;
    private Spinner major;
    private EditText password;
    private EditText email;
    Button save;
    String ids;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public String getIDs(){return ids;}
    FirebaseAuth  firebaseAuth;
    @Override
    //start xml
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);

//find elements
        name = (EditText) findViewById(R.id.name);
        school = (EditText) findViewById(R.id.school);
        age = (EditText) findViewById(R.id.age);
        location = (EditText) findViewById(R.id.location);
        description = (EditText) findViewById(R.id.description);
        gpa = (EditText) findViewById(R.id.gpa);
        major = (Spinner) findViewById(R.id.spinner1);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password2);
        save = (Button) findViewById(R.id.save);
        firebaseDatabase = FirebaseDatabase.getInstance();

        String email2=email.getText().toString().trim();
        mAuth = FirebaseAuth.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Students");
//database ref
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//email ver
                sendEmailVerification();

            }



// email ver method
            private void sendEmailVerification() {
                final String email2 = email.getText().toString();
                String password2 = password.getText().toString();
// get email and password to local vars
                String name2 = name.getText().toString();
                //check email and password vars
                if(!TextUtils.isEmpty(name2) && !TextUtils.isEmpty(email2) && !TextUtils.isEmpty(password2)) {
               //create user
                    mAuth.createUserWithEmailAndPassword(email2, password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//saving data in database
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser() != null) {
                                    String user_id = mAuth.getCurrentUser().getUid();

                                    DatabaseReference user_db = databaseReference.child(user_id);
//database ref on user id
                                    user_db.child("Name").setValue(name.getText().toString().trim());
                                    user_db.child("Age").setValue(age.getText().toString().trim());
                                    user_db.child("Location").setValue(location.getText().toString().trim());
                                    user_db.child("Description").setValue(description.getText().toString().trim());
                                    user_db.child("GPA").setValue(gpa.getText().toString().trim());
                                    user_db.child("School").setValue(school.getText().toString().trim());
                                    user_db.child("Major").setValue(major.getSelectedItem().toString());
                                    user_db.child("Email").setValue(email.getText().toString());
                                    user_db.child("UID").setValue(user_id);
                                //sending the email ver and creating user object from create user call
                                    FirebaseUser    user    =  mAuth.getCurrentUser();
                                    if(mAuth.getCurrentUser() != null)
                                        user.sendEmailVerification()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Intent intent = new Intent(StudentReg.this, com.dan.ututor.System.Log.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                                });


                                }
                            }

                        }
                    });


                }
            }




        });}}





