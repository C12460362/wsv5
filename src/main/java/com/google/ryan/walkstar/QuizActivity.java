package com.google.ryan.walkstar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private QuestionLibrary mQustionLibrary = new QuestionLibrary();

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;

    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mScoreView = (TextView) findViewById(R.id.score);
        mQuestionView = (TextView) findViewById(R.id.question);
        mButtonChoice1 = (Button) findViewById(R.id.choice1);
        mButtonChoice2 = (Button) findViewById(R.id.choice2);
        mButtonChoice3 = (Button) findViewById(R.id.choice3);
        Quesion();
        updateQuestion();



    }
    public void Quesion(){

    mButtonChoice1.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view) {
            if (mQuestionNumber < 4) {
                if (mButtonChoice1.getText() == mAnswer) {
                    mScore = mScore + 1000;
                    updateScore(mScore);
                    updateQuestion();

                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
            else {
                Intent intent = new Intent(QuizActivity.this,MainActivity.class);
                intent.putExtra("walkstars",mScore);
                intent.putExtra("passScore",true);
                startActivity(intent);
            }
        }
    }

    );

    mButtonChoice2.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view) {
            if (mQuestionNumber < 4) {
                if (mButtonChoice2.getText() == mAnswer) {
                    mScore = mScore + 1000;
                    updateScore(mScore);
                    updateQuestion();

                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
            else {
                Intent intent = new Intent(QuizActivity.this,MainActivity.class);
                intent.putExtra("walkstars",mScore);
                intent.putExtra("passScore",true);
                startActivity(intent);
            }
        }
    }

    );


    mButtonChoice3.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view) {
            if (mQuestionNumber < 4) {
                if (mButtonChoice3.getText() == mAnswer) {
                    mScore = mScore + 1000;
                    updateScore(mScore);
                    updateQuestion();

                    Toast.makeText(QuizActivity.this, "Correct", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(QuizActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                    updateQuestion();
                }
            }
            else {
                Intent intent = new Intent(QuizActivity.this,MainActivity.class);
                intent.putExtra("walkstars",mScore);
                intent.putExtra("passScore",true);
                startActivity(intent);
            }
        }
    }

    );
}
    private void updateQuestion(){
        mQuestionView.setText(mQustionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQustionLibrary.getChoice1(mQuestionNumber));
        mButtonChoice2.setText(mQustionLibrary.getChoice2(mQuestionNumber));
        mButtonChoice3.setText(mQustionLibrary.getChoice3(mQuestionNumber));

        mAnswer = mQustionLibrary.getCorrectAnswer(mQuestionNumber);
        mQuestionNumber++;

    }

    private void updateScore(int point){
        mScoreView.setText("" + mScore);

    }
}