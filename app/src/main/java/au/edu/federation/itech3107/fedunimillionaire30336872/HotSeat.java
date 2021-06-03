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
import java.util.Timer;
import java.util.TimerTask;

import DataBase.AppDatabase;
import DataBase.Entity.QuestionEntity;
import DataBase.QuestionDao;

import static java.lang.Math.random;

public class HotSeat extends AppCompatActivity {

    private TextView txtHsCurrentValue, txtHsSafeMoney, txtHsQuestionNum, txtHsQuestion,txtHsTimer;
    private RadioGroup Hsanswer;
    private RadioButton Hsanswer1, Hsanswer2, Hsanswer3, Hsanswer4;
    private Button Hssubmit;
    // int number means which question of user is doing now, int answerNumber means which answer is chosen by user. answerNumber means user has not choose answer.
    private int Hsnumber=1, HsanswerNumber=-1;
    private String  HsquestionNum = "1/11",
            HscurrentMoney,
            HsSafeMoney;


//    instead of database function.

    private List<OnlineQuestionEntity> questionList;
    private OnlineQuestionEntity question;
    private List<String> incorrect_answer;
    private int random=0;

    //Database part

//    AppDatabase appDatabase;
//    QuestionDao questionDao;
//    List<QuestionEntity> questionEntities;
//    QuestionEntity questionEntity;


    private Timer timerDisplay, timerFail;
    private TimerTask timerTask1, timerTask2;
    private int delayMs=0, period1=1000, period2=15000;
    public int a=15;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_seat);


//        Deprecated
//        appDatabase = AppDatabase.getDatabase(this.getApplicationContext());
//        questionDao = appDatabase.getQuestionDao();
//        questionEntities = questionDao.selectAllQuestion();

        questionList = GsonFunction.getQuestionFromJsonForm(getApplicationContext());

        findId();
        setTimer();
        displayQuestion(1);
        onClick();
    }

    public void onPause(){
        super.onPause();
        timerTask1.cancel();
        timerTask2.cancel();
    }

    // find the View component;
    private void findId(){
        txtHsCurrentValue = findViewById(R.id.HscurrentValue);
        txtHsSafeMoney = findViewById(R.id.HssafeMoney);
        txtHsQuestionNum = findViewById(R.id.HsquestionNum);
        txtHsQuestion = findViewById(R.id.Hsquestion);
        Hsanswer = findViewById(R.id.HsanswerGroup);
        Hssubmit = findViewById(R.id.Hssubmit);
        Hsanswer1 = findViewById(R.id.HsradioButton);
        Hsanswer2 = findViewById(R.id.HsradioButton1);
        Hsanswer3 = findViewById(R.id.HsradioButton2);
        Hsanswer4 = findViewById(R.id.HsradioButton3);
        txtHsTimer = findViewById(R.id.Timer);
    }


    // set onclick listeners
    private void onClick(){
        Hssubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

        // use radio group's position to confirm the user's choose;
        Hsanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                        case R.id.HsradioButton:
                            HsanswerNumber = 1;
                            break;
                        case R.id.HsradioButton1:
                            HsanswerNumber = 2;
                            break;
                        case R.id.HsradioButton2:
                            HsanswerNumber = 3;
                            break;
                        case R.id.HsradioButton3:
                            HsanswerNumber = 4;
                            break;
                    }
                    Log.d("HotSeatHsanswerNumber", String.valueOf(HsanswerNumber));
                }
            }
        });
    }



    //Pick and display the data from database.
    private void displayQuestion(int number){


//        Deprecated
//        questionEntity = questionEntities.get(number-1);
//        txtHsQuestion.setText(questionEntity.getQuestion());
//        Hsanswer1.setText(questionEntity.getAnswer1());
//        Hsanswer2.setText(questionEntity.getAnswer2());
//        Hsanswer3.setText(questionEntity.getAnswer3());
//        Hsanswer4.setText(questionEntity.getAnswer4());

        getQuestion(number);
        currentValue();
        safeMoney();
        currentQuestion();

        if(number!=1){
            Hsanswer.clearCheck();
            timerTask1.cancel();
            timerTask2.cancel();
            a=15;
        }

        timerDisplay.scheduleAtFixedRate(task1(),delayMs,period1);
        timerFail.schedule(task2(),period2);
    }


    // check Function
    private void check(){

        if(selectOrNot()){

            if(selectCorrectAnswer()){
                // check whether finish the question.
                if(Hsnumber<11){
                    Hsnumber++;
                    displayQuestion(Hsnumber);
                    HsanswerNumber=-1;
                }
                else {
                    Hsnumber = 1;
                    HsanswerNumber=-1;
                    Intent intent = new Intent(HotSeat.this, SummaryPage.class);
                    intent.putExtra("complete", true);
                    startActivity(intent);
                }

            }
            else {
                Intent intent = new Intent(HotSeat.this, SummaryPage.class);
                intent.putExtra("complete",false);
                intent.putExtra("NumberComplete", Hsnumber);
                Log.d("test",String.valueOf(Hsnumber));
                Hsnumber = 1;
                HsanswerNumber=-1;
                startActivity(intent);
            }

        }
        else {
            Toast.makeText(HotSeat.this, "You haven't select answer yet.", Toast.LENGTH_LONG).show();
        }
    }


    // check whether user chosen the answer.
    private boolean selectOrNot(){
        if(HsanswerNumber==-1){
            return false;
        }
        else{
            return true;
        }
    }

    //check whether choose correct answer.
    private boolean selectCorrectAnswer(){

        if(HsanswerNumber==(random+1)){
            return true;
        }
        else return false;

//        //        Deprecated
//        if(HsanswerNumber==questionEntity.getCorrectAnswer()){
//            return true;
//        }
//        else return false;
    }

    // display current value
    private void currentValue(){
        switch (Hsnumber){
            case 1: txtHsCurrentValue.setText("Current Money: $"+ 0);
                break;
            case 2: txtHsCurrentValue.setText("Current Money: $"+ 1000);
                break;
            case 3: txtHsCurrentValue.setText("Current Money: $"+ 2000);
                break;
            case 4: txtHsCurrentValue.setText("Current Money: $"+ 4000);
                break;
            case 5: txtHsCurrentValue.setText("Current Money: $"+ 8000);
                break;
            case 6: txtHsCurrentValue.setText("Current Money: $"+ 16000);
                break;
            case 7: txtHsCurrentValue.setText("Current Money: $"+ 32000);
                break;
            case 8: txtHsCurrentValue.setText("Current Money: $"+ 64000);
                break;
            case 9: txtHsCurrentValue.setText("Current Money: $"+ 125000);
                break;
            case 10: txtHsCurrentValue.setText("Current Money: $"+ 250000);
                break;
            case 11: txtHsCurrentValue.setText("Current Money: $"+ 500000);
                break;
        }
    }

    //display safe Money
    private void safeMoney(){
        if(Hsnumber>1){
            if(Hsnumber>=2 && Hsnumber<7){
                txtHsSafeMoney.setText("Safe Money: $1000");
            }
            if(Hsnumber>=7 && Hsnumber<11){
                txtHsSafeMoney.setText("Safe Money: $3200");
            }
        }
        else{
            txtHsSafeMoney.setText("Safe Money: $0");
        }
    }


    private void currentQuestion(){
        txtHsQuestionNum.setText(Hsnumber+"/11");
    }


// use this function to ipur data from Json file instead of database system.
    private void getQuestion(int number){

        question = questionList.get(number-1);
        incorrect_answer= question.getIncorrect_answers();

//        set random range: 0<=random<4  {0,1,2,3}
        random = (int)(Math.random()*4);
        incorrect_answer.add(random,question.getCorrect_answer());

        txtHsQuestion.setText(question.getQuestion());
        Hsanswer1.setText(incorrect_answer.get(0));
        Hsanswer2.setText(incorrect_answer.get(1));
        Hsanswer3.setText(incorrect_answer.get(2));
        Hsanswer4.setText(incorrect_answer.get(3));

    }


    private void setTimer(){

        timerDisplay =new Timer();
        timerFail = new Timer();

    }


    private TimerTask task1(){
        timerTask1 = new TimerTask() {
            @Override
            public void run() {
                txtHsTimer.setText("Time: "+ String.valueOf(a) +"s");
                Log.d("timer","Time: "+ String.valueOf(a) +"s");
                a--;
            }
        };
        return timerTask1;
    }


    private TimerTask task2(){
        timerTask2 = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(HotSeat.this, SummaryPage.class);
                intent.putExtra("complete",false);
                intent.putExtra("NumberComplete", Hsnumber);
                Log.d("test",String.valueOf(Hsnumber));
                Hsnumber = 1;
                startActivity(intent);
            }
        };
        return  timerTask2;
    }



}