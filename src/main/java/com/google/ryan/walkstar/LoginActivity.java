package com.google.ryan.walkstar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Button alogin;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alogin = (Button)findViewById(R.id.cLogin);
        username = (EditText)findViewById(R.id.cName);
        password = (EditText)findViewById(R.id.cPass);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        alogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper db = new DbHelper(view.getContext());
                String userValue = username.getText().toString();
                String passValue = password.getText().toString();
                boolean accountMatch = db.validateUser(userValue,passValue);
                int charUserLength = userValue.length();
                int charPassLength = passValue.length();

                if(accountMatch){
                    Intent success = new Intent(LoginActivity.this, PickChallengeActivity.class);
                    startActivity(success);
                }
                else if(userValue.equals("") && passValue.equals("")){
                    Toast.makeText(LoginActivity.this,"Fields Empty", Toast.LENGTH_SHORT).show();
                }
                else if(charUserLength < 5 && charPassLength < 5){ // At least 6 characters anything less is too short
                    Toast.makeText(LoginActivity.this,"Characters Too Short", Toast.LENGTH_SHORT).show();
                }
                else{Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();}
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
