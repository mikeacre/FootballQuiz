package com.example.mikeacre.footballquiz;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    int questionsLeft = 4;
    int totalQuestions = questionsLeft;
    int currQuestion = 0;
    int correctAnswers =0;
    String[] allQuestions;
    int[] allCorrect = new int[questionsLeft];
    int[] storeIds = new int[questionsLeft];
    private RadioGroup aRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        allQuestions = res.getStringArray(R.array.question);
        loadQuestions();
        final Button button = (Button) findViewById(R.id.finished);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finished();
            }
        });
    }

    private void finished(){
        int currCheck = 0;
        int totalCorrect = 0;
        final EditText textBox = (EditText) findViewById(R.id.textAnswer);
        String textAnswer = textBox.getText().toString();
        final CheckBox cb1 = (CheckBox) findViewById(R.id.cb1);
        final CheckBox cb2 = (CheckBox) findViewById(R.id.cb2);
        final CheckBox cb3 = (CheckBox) findViewById(R.id.cb3);
        final CheckBox cb4 = (CheckBox) findViewById(R.id.cb4);
        final CheckBox cb5 = (CheckBox) findViewById(R.id.cb5);
        final CheckBox cb6 = (CheckBox) findViewById(R.id.cb6);

        if(cb2.isChecked()&&cb3.isChecked()&&cb5.isChecked())
            if(!(cb1.isChecked()||cb4.isChecked()||cb6.isChecked()))
                totalCorrect++;

        if(textAnswer.equals("52"))
            totalCorrect=totalCorrect+1;

        while(currCheck < totalQuestions) {
            RadioGroup radioButtonGroup = (RadioGroup) findViewById(storeIds[currCheck]);
            int radioButtonID = radioButtonGroup.getCheckedRadioButtonId();
            if (radioButtonID == allCorrect[currCheck])
                totalCorrect = totalCorrect + 1;
            currCheck = currCheck+1;
        }
        Toast.makeText(getBaseContext(), "You got "+totalCorrect+" answers correct", Toast.LENGTH_SHORT).show();
    }

    private void loadQuestions(){
        while(questionsLeft > 0){
            writeQuestion();
        }
    }

    private void addAnswer(String content,int correct)
    {
        View main2 = findViewById(R.id.main);
        RadioButton aOne = new RadioButton(this);
        aOne.setText(content);
        aOne.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        ((LinearLayout) aRadio).addView(aOne);

        if(correct == 1 )
            allCorrect[currQuestion] = aOne.getId();
    }

    private void writeQuestion(){
        String[] thisQuestion = allQuestions[currQuestion].split("@@");

        int answers = 3;
        int currAnswer=1;

        View main = findViewById(R.id.main);
        TextView qTitle = new TextView(this);
        qTitle.setText(thisQuestion[0]);
        qTitle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        ((LinearLayout) main).addView(qTitle);

        aRadio = new RadioGroup(this);
        aRadio.setId(currQuestion+999);
        int thisId= aRadio.getId();
        storeIds[currQuestion] = thisId;
        aRadio.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        ((LinearLayout) main).addView(aRadio);

        while(answers > 0) {

            int correct = 0;
            int thisQuestionInt = Integer.parseInt(thisQuestion[4]);
            if(thisQuestionInt == currAnswer)
                correct=1;
            addAnswer(thisQuestion[currAnswer], correct);
            answers = answers-1;
            currAnswer = currAnswer+1;
        }

        questionsLeft = questionsLeft-1;
        currQuestion = currQuestion+1;
    }
}
