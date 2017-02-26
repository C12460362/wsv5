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

public class RegisterActivity extends AppCompatActivity {

    private Button reg;
    private EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        reg = (Button)findViewById(R.id.register);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper db = new DbHelper(v.getContext());
                Intent lOptions = new Intent(RegisterActivity.this, LoginOptionActivity.class);
                user = (EditText)findViewById(R.id.username);
                pass = (EditText)findViewById(R.id.password);
                String userValue = user.getText().toString();
                String passValue = pass.getText().toString();
                int userLen = userValue.length();
                int passLen = passValue.length();
                boolean userMatch = db.sameUser(userValue);

                if (userMatch) { //Checks to see if the UserName already exists
                    Toast.makeText(RegisterActivity.this, "UserName Already Taken", Toast.LENGTH_LONG).show();
                }
                else if (userValue.equals("") && passValue.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Fields Empty", Toast.LENGTH_SHORT).show();
                }
                else if (userLen <= 3 && passLen <= 5) {
                    Toast.makeText(RegisterActivity.this, "Characters too short", Toast.LENGTH_SHORT).show();
                }
                else {
                    db.addUser(new User( userValue , passValue));
                    Toast.makeText(RegisterActivity.this, "User Added", Toast.LENGTH_SHORT).show();
                    startActivity(lOptions);
                }

            }
        });
    }


}
