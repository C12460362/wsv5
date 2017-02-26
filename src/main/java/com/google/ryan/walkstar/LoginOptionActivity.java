package com.google.ryan.walkstar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LoginOptionActivity extends AppCompatActivity {
    private Button aCMaker,aChallenger,regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        aCMaker = (Button)findViewById(R.id.pickChallenge);
        aChallenger = (Button)findViewById(R.id.viewChallenges);
        regBtn = (Button)findViewById(R.id.regbtn);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        aCMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent options = new Intent(LoginOptionActivity.this, ChallengeMakerActivity.class);
                startActivity(options);
            }
        });

        aChallenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent options = new Intent(LoginOptionActivity.this, LoginActivity.class);
                startActivity(options);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent options = new Intent(LoginOptionActivity.this, RegisterActivity.class);
                startActivity(options);
            }
        });

    }

}
