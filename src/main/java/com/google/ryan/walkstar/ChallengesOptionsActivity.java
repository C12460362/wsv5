package com.google.ryan.walkstar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.nio.Buffer;

public class ChallengesOptionsActivity extends AppCompatActivity{
    private DBChallengeHelper myDB;
    private Button challenge1,challenge2,challenge3;
    public StringBuffer stepAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenges);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        myDB = new DBChallengeHelper(this);

        challenge1 = (Button)findViewById(R.id.ch1);
        challenge2 = (Button)findViewById(R.id.ch2);
        challenge3 = (Button)findViewById(R.id.ch3);


        ChallengeOne();
        ChallengeTwo();
        ChallengeThree();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public boolean ChallengeOne(){
        Cursor res = myDB.getChallenge1();
        if(res.getCount() == 0 ){
            Toast toast = Toast.makeText(getApplicationContext(),"Error Nothing Found",Toast.LENGTH_SHORT);
            toast.show();
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("Challenge: " + res.getString(0)+ "\n ");
            buffer.append("Steps: " + res.getString(1)+ "\n ");
        }
        Cursor steps = myDB.getSteps1();
        StringBuffer buffer1 = new StringBuffer();
        while (steps.moveToNext()){
            stepAmount=buffer1.append(steps.getString(0));
        }

        challenge1.setText(buffer.toString());
        challenge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ChallengesOptionsActivity.this, MainActivity.class);
                intent.putExtra("challenge1",true);
                intent.putExtra("challengeOne",stepAmount.toString());
                startActivity(intent);

            }
        });
        return true;

    }

    public boolean ChallengeTwo(){
        Cursor res = myDB.getChallenge2();
        if(res.getCount() == 0 ){
            Toast toast = Toast.makeText(getApplicationContext(),"Error Nothing Found",Toast.LENGTH_SHORT);
            toast.show();
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            //buffer.append("CHALLENGE NAME: " + res.getString(1)+ "\n");
            buffer.append("Challenge: " + res.getString(0)+ "\n ");
            buffer.append("Steps: " + res.getString(1)+ "\n ");
        }
        Cursor steps = myDB.getSteps2();
        StringBuffer buffer1 = new StringBuffer();
        while (steps.moveToNext()){
            stepAmount=buffer1.append(steps.getString(0));
        }
        challenge2.setText(buffer.toString());
        challenge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ChallengesOptionsActivity.this, MainActivity.class);
                intent.putExtra("challenge2",true);
                intent.putExtra("challengeTwo",stepAmount.toString());
                startActivity(intent);

            }
        });
        return true;

    }

    public boolean ChallengeThree(){
        Cursor res = myDB.getChallenge3();
        if(res.getCount() == 0 ){
            Toast toast = Toast.makeText(getApplicationContext(),"Error Nothing Found",Toast.LENGTH_SHORT);
            toast.show();
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            //buffer.append("CHALLENGE NAME: " + res.getString(1)+ "\n");
            buffer.append("Challenge: " + res.getString(0)+ "\n ");
            buffer.append("Steps: " + res.getString(1)+ "\n ");
        }
        Cursor steps = myDB.getSteps3();
        StringBuffer buffer1 = new StringBuffer();
        while (steps.moveToNext()){
            stepAmount=buffer1.append(steps.getString(0));
        }
        challenge3.setText(buffer.toString());
        challenge3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ChallengesOptionsActivity.this, MainActivity.class);
                intent.putExtra("challenge3",true);
                intent.putExtra("challengeThree",stepAmount.toString());
                startActivity(intent);

            }
        });
        return true;
    }


}
