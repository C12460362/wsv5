package com.google.ryan.walkstar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PickChallengeActivity extends AppCompatActivity {
    DBChallengeHelper myDB;
    private Button pickChallengeBtn;
    private Button viewChallengeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_challenge);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pickChallengeBtn = (Button)findViewById(R.id.pickChallenge);
        viewChallengeBtn = (Button)findViewById(R.id.viewChallenges);
        myDB = new DBChallengeHelper(this);
        viewChallenge();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void viewChallenge(){
        viewChallengeBtn.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(PickChallengeActivity.this, ChallengesOptionsActivity.class);
                        startActivity(intent);

                    }
                }
        );
    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }

}
