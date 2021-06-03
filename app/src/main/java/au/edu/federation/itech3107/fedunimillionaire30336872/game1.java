package au.edu.federation.itech3107.fedunimillionaire30336872;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import DataBase.AppDatabase;
import DataBase.Entity.QuestionEntity;
import DataBase.QuestionDao;

public class game1 extends AppCompatActivity {

    private TextView txtCurrentValue, txtSafeMoney, txtQuestionNum, txtQuestion;
    private RadioGroup answer;
    private RadioButton answer1, answer2, answer3, answer4;
    private Button submit;
    // int number means which question of user is doing now, int answerNumber means which answer is chosen by user. answerNumber means user has not choose answer.
    private int number=1, answerNumber=-1;
    private String  questionNum = "1/15",
                    currentMoney,
                    SafeMoney;


    //Database part

    AppDatabase appDatabase;
    QuestionDao questionDao;
    List<QuestionEntity> questionEntities;
    QuestionEntity questionEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        appDatabase = AppDatabase.getDatabase(this.getApplicationContext());
        questionDao = appDatabase.getQuestionDao();
        questionEntities = questionDao.selectAllQuestion();

        findId();
        displayQuestion(1);
        onClick();
    }



    // find the View component;
    private void findId(){
        txtCurrentValue = findViewById(R.id.currentValue);
        txtSafeMoney = findViewById(R.id.safeMoney);
        txtQuestionNum = findViewById(R.id.questionNum);
        txtQuestion = findViewById(R.id.question);
        answer = findViewById(R.id.answerGroup);
        submit = findViewById(R.id.submit);
        answer1 = findViewById(R.id.radioButton);
        answer2 = findViewById(R.id.radioButton1);
        answer3 = findViewById(R.id.radioButton2);
        answer4 = findViewById(R.id.radioButton3);
    }

    // set onclick listeners
    private void onClick(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

        // use radio group's position to confirm the user's choose;
        answer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                /*
                this judgment statement is very import.  ------if(group.findViewById(checkedId)!=null)
                first I want to clean the answer of user chosen before to avoid if user start new question,
                there is one answer already been choose which is same position of the previous answer.
                so use  answer.clearcheck();
                however, there is always an exception:
                 E/AndroidRuntime: FATAL EXCEPTION: main
                     Process: au.edu.federation.itech3107.fedunimillionaire30336872, PID: 14819
                     java.lang.NullPointerException: Attempt to invoke virtual method 'int android.widget.RadioButton.getId()' on a null object reference

                 the reason is:
                 when use cleancheck() method , radioButton's check state changed, it will call this method,
                 however there is not any button clicked, therefore it will point to a null object.
                 -- state changed but not clicked!

                 so this statement is needed.
                                          ---Hanlin Zheng
                 */
                if(group.findViewById(checkedId)!=null){
                    RadioButton chosenAnswer = group.findViewById(checkedId);
                    switch (chosenAnswer.getId()){
                        case R.id.radioButton:
                            answerNumber = 1;
                            break;
                        case R.id.radioButton1:
                            answerNumber = 2;
                            break;
                        case R.id.radioButton2:
                            answerNumber = 3;
                            break;
                        case R.id.radioButton3:
                            answerNumber = 4;
                            break;
                    }
                    Log.d("test", String.valueOf(answerNumber));
                }
            }
        });
    }



    //Pick and display the data from database.
    private void displayQuestion(int number){
        questionEntity = questionEntities.get(number-1);
        txtQuestion.setText(questionEntity.getQuestion());
        answer1.setText(questionEntity.getAnswer1());
        answer2.setText(questionEntity.getAnswer2());
        answer3.setText(questionEntity.getAnswer3());
        answer4.setText(questionEntity.getAnswer4());
        currentValue();
        safeMoney();
        currentQuestion();

        if(number!=1){
            answer.clearCheck();
        }

    }


    // check Function
    private void check(){

        if(selectOrNot()){

            if(selectCorrectAnswer()){
                // check whether finish the question.
                if(number<11){
                    number++;
                    displayQuestion(number);
                    answerNumber=-1;
                }
                else {
                    number = 1;
                    answerNumber=-1;
                    Intent intent = new Intent(game1.this, SummaryPage.class);
                    intent.putExtra("complete", true);
                    startActivity(intent);
                }

            }
            else {
                Intent intent = new Intent(game1.this, SummaryPage.class);
                intent.putExtra("complete",false);
                intent.putExtra("NumberComplete", number);
                Log.d("test",String.valueOf(number));
                number = 1;
                answerNumber=-1;
                startActivity(intent);
            }

        }
        else{
            Toast.makeText(game1.this, "You haven't select answer yet.", Toast.LENGTH_LONG).show();
        }
    }





    // check whether user chosen the answer.
    private boolean selectOrNot(){
        if(answerNumber==-1){
            return false;
        }
        else{
            return true;
        }
    }

    //check whether choose correct answer.
    private boolean selectCorrectAnswer(){
        if(answerNumber==questionEntity.getCorrectAnswer()){
            return true;
        }
        else return false;
    }

    // display current value
    private void currentValue(){
        switch (number){
            case 1: txtCurrentValue.setText("Current Money: $"+ 0);
            break;
            case 2: txtCurrentValue.setText("Current Money: $"+ 1000);
                break;
            case 3: txtCurrentValue.setText("Current Money: $"+ 2000);
                break;
            case 4: txtCurrentValue.setText("Current Money: $"+ 4000);
                break;
            case 5: txtCurrentValue.setText("Current Money: $"+ 8000);
                break;
            case 6: txtCurrentValue.setText("Current Money: $"+ 16000);
                break;
            case 7: txtCurrentValue.setText("Current Money: $"+ 32000);
                break;
            case 8: txtCurrentValue.setText("Current Money: $"+ 64000);
                break;
            case 9: txtCurrentValue.setText("Current Money: $"+ 125000);
                break;
            case 10: txtCurrentValue.setText("Current Money: $"+ 250000);
                break;
            case 11: txtCurrentValue.setText("Current Money: $"+ 500000);
                break;
        }
    }

    //display safe Money
    private void safeMoney(){
        if(number>1){
            if(number>=2 && number<7){
                txtSafeMoney.setText("Safe Money: $1000");
            }
            if(number>=7 && number<11){
                txtSafeMoney.setText("Safe Money: $3200");
            }
        }
        else{
            txtSafeMoney.setText("Safe Money: $0");
        }
    }


    private void currentQuestion(){
        txtQuestionNum.setText(number+"/11");
    }



}