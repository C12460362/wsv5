package com.google.ryan.walkstar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ChallengeActivity extends AppCompatActivity{
    DBChallengeHelper myDB;
    TextView stepamount;
    EditText cDetail;
    EditText getID;
    TextView dayamount;
    SeekBar seekBar;
    SeekBar seekBar1;
    int noSteps = 1000;
    int noDays = 1;
    Spinner selectchallenge;
    ArrayAdapter<CharSequence> adapter;
    Button challenge;
    Button btnChallenge;
    Button btnDelete;
    Button btnUpdate;
    public final String choice = "10000";
    public String challSelected;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);


        myDB = new DBChallengeHelper(this);
        challenge = (Button)findViewById(R.id.createChallenge);
        btnChallenge =(Button)findViewById(R.id.database);
        btnDelete = (Button)findViewById(R.id.delete);
        btnUpdate = (Button)findViewById(R.id.update);
        cDetail = (EditText) findViewById(R.id.challengeDetail);

        stepamount = (TextView)findViewById(R.id.StepAmount);
        stepamount.setText(""+noSteps);

        dayamount = (TextView)findViewById(R.id.dayAmount);
        dayamount.setText("" + noDays);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(10000);
        seekBar.setProgress(noSteps);


        seekBar1 = (SeekBar)findViewById(R.id.seekBar2);
        seekBar1.setMax(7);
        seekBar1.setProgress(noDays);

        selectchallenge = (Spinner)findViewById(R.id.selectChallenge);
        adapter = ArrayAdapter.createFromResource(this,R.array.challenges,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectchallenge.setAdapter(adapter);

        selectchallenge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //choice = adapterView.getItemAtPosition(i).toString());
                Toast.makeText(getBaseContext(),adapterView.getItemAtPosition(i)+"Selected",Toast.LENGTH_LONG).show();
                challSelected = (String) adapterView.getItemAtPosition(i);

            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progress = ((int)Math.round(progress/noSteps))*noSteps;
                seekBar.setProgress(progress);
                stepamount.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                noDays = i;
                dayamount.setText("" + noDays);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        AddData();
        viewChallenge();
        DeleteData();
        UpdateData();
    }

    public void DeleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean deletedRows = myDB.deleteData();
                        if(deletedRows)
                            Toast.makeText(ChallengeActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ChallengeActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
    public void UpdateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDB.updateData(getID.getText().toString(),
                                challSelected,
                                cDetail.getText().toString(),
                                stepamount.getText().toString(),
                                dayamount.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(ChallengeActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ChallengeActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }


    public void AddData(){
        challenge.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDB.insertData(//choice.toString(),
                                challSelected,
                                cDetail.getText().toString(),
                                stepamount.getText().toString(),
                                dayamount.getText().toString());


                        if(isInserted)
                            Toast.makeText(ChallengeActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ChallengeActivity.this,"Data Insert Error",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ChallengeActivity.this, ChallengesOptionsActivity.class);
                        intent.putExtra("Value",stepamount.getText().toString());
                        intent.putExtra("passSteps",true);
                        startActivity(intent);
                    }
                }
        );
    }

    public void viewChallenge(){
        btnChallenge.setOnClickListener(
                new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Cursor res = myDB.getAllData();
                        if(res.getCount() == 0 ){
                            showMessage("Error", "Nothing Found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()){
                            //buffer.append("CHALLENGE NAME: " + res.getString(1)+ "\n");
                            buffer.append("ID: " + res.getString(0)+ "\n ");
                            buffer.append("Challenge: " + res.getString(1)+ "\n ");
                            buffer.append("Steps : " + res.getString(2)+ "\n ");
                        }

                        showMessage("Challenges",buffer.toString());

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
