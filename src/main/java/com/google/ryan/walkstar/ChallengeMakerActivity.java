package com.google.ryan.walkstar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChallengeMakerActivity extends AppCompatActivity {

    private EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_maker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button aClogin = (Button)findViewById(R.id.cmloginBtn);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        aClogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = new DbHelper(view.getContext());
                user = (EditText) findViewById(R.id.ParentName);
                pass = (EditText) findViewById(R.id.ParentPass);
                String userValue = user.getText().toString();
                String passValue = pass.getText().toString();
                boolean accountMatch = db.validateUser(userValue,passValue);
                int charUserLength = userValue.length();
                int charPassLength = passValue.length();

                if(accountMatch){
                    Intent success = new Intent(ChallengeMakerActivity.this, ParentMainActivity.class);
                    startActivity(success);
                }
                else if(userValue.equals("") && passValue.equals("")){
                    Toast.makeText(ChallengeMakerActivity.this,"Fields Empty", Toast.LENGTH_SHORT).show();
                }
                else if(charUserLength < 5 && charPassLength < 5){ // At least 6 characters anything less is too short
                    Toast.makeText(ChallengeMakerActivity.this,"Characters Too Short", Toast.LENGTH_SHORT).show();
                }
                else{Toast.makeText(ChallengeMakerActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();}
            }
        });


    }

}