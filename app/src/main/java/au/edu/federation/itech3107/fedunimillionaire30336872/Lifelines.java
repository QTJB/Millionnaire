package au.edu.federation.itech3107.fedunimillionaire30336872;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Lifelines extends AppCompatActivity {

    private TextView txtLlCurrentValue, txtLlSafeMoney, txtLlQuestionNum, txtLlQuestion, txtLlTimer;
    private RadioGroup Llanswer;
    private RadioButton Llanswer1, Llanswer2, Llanswer3, Llanswer4;
    private Button Llsubmit, btnHalf, btnAudience, btnSwitch;
    // int number means which question of user is doing now, int answerNumber means which answer is chosen by user. answerNumber means user has not choose answer.
    private int Llnumber = 1, LlanswerNumber = -1;
    private String LlquestionNum = "1/11",
            LlcurrentMoney,
            LlSafeMoney;

//    instead of database function.
    private List<OnlineQuestionEntity> questionList;
    private OnlineQuestionEntity question;
    private List<String> incorrect_answer;
    private int random = 0;

    //Database part

//    AppDatabase appDatabase;
//    QuestionDao questionDao;
//    List<QuestionEntity> questionEntities;
//    QuestionEntity questionEntity;
    private Timer timerDisplay, timerFail;
    private TimerTask timerTask1, timerTask2;
    private int delayMs = 0, period1 = 1000, period2 = 15000;
    public int a = 15;
    //    new function
    private boolean half = false, audience = false, switchs = false;
    private int oddOrEven;
//    test
    private String  websiteAddress= "https://opentdb.com/api.php?amount=12&type=multiple";
    private String check;
    private String json=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifelines);

//        InternetConnect.context=getApplicationContext();
//        questionList = GsonFunction.getQuestionFromJsonForm(getApplicationContext());



//        questionList=GsonFunction.JsonStringToObjectList(json);

         questionList = GsonFunction.getQuestionFromJsonForm(getApplicationContext());

//        findId();
//        setTimer();
//        displayQuestion(1);
//        onClick();

    }


    @Override
    protected void onResume() {
        super.onResume();
//
//        InternetConnect.context=getApplicationContext();

//
//        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

//        questionList = GsonFunction.getQuestionFromJsonForm(getApplicationContext());


        if(isConnected(getApplicationContext())){
            final InternetConnect internetConnect =new InternetConnect();
            InternetConnect.get(this, websiteAddress, new InternetConnect.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    Log.d("internet", result);
                    json = result;
                    Log.d("internet", json);
                    json = json.substring(29, json.length() - 1);
                    Log.d("internet", json);


                    if (json != null || json == "") {
//                       questionList = GsonFunction.getQuestionFromJsonForm(getApplicationContext());
                        questionList = GsonFunction.JsonStringToObjectList(json);
                        Toast.makeText(getApplicationContext(), "Connected to Network", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Cannot connect to network", Toast.LENGTH_LONG).show();
                    }

                    findId();
                    setTimer();
                    displayQuestion(1);
                    onClick();
                }

            });
            }else {
            Toast.makeText(getApplicationContext(), "Cannot connect to network", Toast.LENGTH_LONG).show();
            findId();
            setTimer();
            displayQuestion(1);
            onClick();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        timerTask1.cancel();
        timerTask2.cancel();
    }



    private  boolean isConnected(Context context){

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wiFiConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConnect = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wiFiConnect != null && wiFiConnect.isConnected())|| (mobileConnect != null && mobileConnect.isConnected())){
            return true;
        }
        else return false;
    }



//    public void getString(final VolleyCallback callback) {
//
//        final RequestQueue requestQueue = Volley.newRequestQueue(Lifelines.this);
//
//        final StringRequest strReq = new StringRequest(Request.Method.POST, websiteAddress, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                callback.onSuccess(response);
//                Toast.makeText(getApplicationContext(),"Connected to NetWork!",Toast.LENGTH_LONG).show();
//                requestQueue.stop();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"Cannot connect to NetWork!",Toast.LENGTH_LONG).show();
//                callback.onSuccess(null);
//                requestQueue.stop();
//            }
//        });
//
//        requestQueue.add(strReq);
//    }


    public interface VolleyCallback{
        void onSuccess(String result);
    }




    // find the View component;
    private void findId() {
        txtLlCurrentValue = findViewById(R.id.LlcurrentValue);
        txtLlSafeMoney = findViewById(R.id.LlsafeMoney);
        txtLlQuestionNum = findViewById(R.id.LlquestionNum);
        txtLlQuestion = findViewById(R.id.Llquestion);
        Llanswer = findViewById(R.id.LlanswerGroup);
        Llsubmit = findViewById(R.id.Llsubmit);
        Llanswer1 = findViewById(R.id.LlradioButton);
        Llanswer2 = findViewById(R.id.LlradioButton1);
        Llanswer3 = findViewById(R.id.LlradioButton2);
        Llanswer4 = findViewById(R.id.LlradioButton3);
        txtLlTimer = findViewById(R.id.LlTimer);
        btnHalf = findViewById(R.id.buttonhalf);
        btnAudience = findViewById(R.id.buttonAudience);
        btnSwitch = findViewById(R.id.buttonSwitch);
    }


    // set onclick listeners
    private void onClick() {
        Llsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
            }
        });

        // use radio group's position to confirm the user's choose;
        Llanswer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
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
                if (group.findViewById(checkedId) != null) {
                    RadioButton chosenAnswer = group.findViewById(checkedId);
                    switch (chosenAnswer.getId()) {
                        case R.id.LlradioButton:
                            LlanswerNumber = 1;
                            break;
                        case R.id.LlradioButton1:
                            LlanswerNumber = 2;
                            break;
                        case R.id.LlradioButton2:
                            LlanswerNumber = 3;
                            break;
                        case R.id.LlradioButton3:
                            LlanswerNumber = 4;
                            break;
                    }
                    Log.d("HotSeatHsanswerNumber", String.valueOf(LlanswerNumber));
                }
            }
        });

        btnHalf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ifUsefunction(btnHalf)) {
                    half = true;
                    btnHalf.setClickable(false);
                    btnHalf.setVisibility(View.INVISIBLE);
                    halffunction();
                }
            }
        });

        btnAudience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ifUsefunction(btnAudience)) {
                    audience = true;
                    btnAudience.setClickable(false);
                    btnAudience.setVisibility(View.INVISIBLE);
                    AudienceMethod();
                }

            }
        });

        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ifUsefunction(btnSwitch)) {
                    switchs = true;
                    btnSwitch.setClickable(false);
                    btnSwitch.setVisibility(View.INVISIBLE);
                    switchQuestion();
                }
            }
        });
    }

    // check whether already used one fuunction at same question.
    private boolean ifUsefunction(Button button) {
        boolean check = false;
        switch (button.getId()) {
            case R.id.buttonhalf:
                if (!audience && !switchs) {
                    check = true;
                } else {
                    check = false;
                }
                break;
            case R.id.buttonAudience:
                if (!half && !switchs) {
                    check = true;
                } else {
                    check = false;
                }
                break;
            case R.id.buttonSwitch:
                if (!half && !audience) {
                    check = true;
                } else {
                    check = false;
                }
                break;
        }
        return check;
    }

    //Pick and display the data from database.
    private void displayQuestion(int number) {


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

        if (number != 1) {
            Llanswer.clearCheck();
            timerTask1.cancel();
            timerTask2.cancel();
            a = 15;
        }

        timerDisplay.scheduleAtFixedRate(task1(), delayMs, period1);
        timerFail.schedule(task2(), period2);
    }

    // check Function
    private void check() {

        if (selectOrNot()) {

            if (selectCorrectAnswer()) {
                // check whether finish the question.
                if (Llnumber < 11) {
                    Llnumber++;
                    displayQuestion(Llnumber);
                    LlanswerNumber = -1;
                    resetAnswerVisiable();
//                  reset these value to complete the function ifUSeFunction(};
                    half = audience = switchs = false;
                } else {
                    Llnumber = 1;
                    LlanswerNumber = -1;

//                  reset these value to complete the function ifUSeFunction(};
                    half = audience = switchs = false;
                    resetAnswerVisiable();
                    Intent intent = new Intent(Lifelines.this, SummaryPage.class);
                    intent.putExtra("complete", true);
                    startActivity(intent);
                }

            } else {
                Intent intent = new Intent(Lifelines.this, SummaryPage.class);
                intent.putExtra("complete", false);
                intent.putExtra("NumberComplete", Llnumber);
                Log.d("test", String.valueOf(Llnumber));
                Llnumber = 1;
                LlanswerNumber = -1;
                resetAnswerVisiable();
//                  reset these value to complete the function ifUSeFunction(};
                half = audience = switchs = false;
                startActivity(intent);
            }


        } else {
            Toast.makeText(Lifelines.this, "You haven't select answer yet.", Toast.LENGTH_LONG).show();
        }
    }


    // check whether user chosen the answer.
    private boolean selectOrNot() {
        if (LlanswerNumber == -1) {
            return false;
        } else {
            return true;
        }
    }

    //check whether choose correct answer.
    private boolean selectCorrectAnswer() {

        if (LlanswerNumber == (random + 1)) {
            return true;
        } else return false;

//        //        Deprecated
//        if(HsanswerNumber==questionEntity.getCorrectAnswer()){
//            return true;
//        }
//        else return false;
    }

    // display current value
    private void currentValue() {
        switch (Llnumber) {
            case 1:
                txtLlCurrentValue.setText("Current Money: $" + 0);
                break;
            case 2:
                txtLlCurrentValue.setText("Current Money: $" + 1000);
                break;
            case 3:
                txtLlCurrentValue.setText("Current Money: $" + 2000);
                break;
            case 4:
                txtLlCurrentValue.setText("Current Money: $" + 4000);
                break;
            case 5:
                txtLlCurrentValue.setText("Current Money: $" + 8000);
                break;
            case 6:
                txtLlCurrentValue.setText("Current Money: $" + 16000);
                break;
            case 7:
                txtLlCurrentValue.setText("Current Money: $" + 32000);
                break;
            case 8:
                txtLlCurrentValue.setText("Current Money: $" + 64000);
                break;
            case 9:
                txtLlCurrentValue.setText("Current Money: $" + 125000);
                break;
            case 10:
                txtLlCurrentValue.setText("Current Money: $" + 250000);
                break;
            case 11:
                txtLlCurrentValue.setText("Current Money: $" + 500000);
                break;
        }
    }

    //display safe Money
    private void safeMoney() {
        if (Llnumber > 1) {
            if (Llnumber >= 2 && Llnumber < 7) {
                txtLlSafeMoney.setText("Safe Money: $1000");
            }
            if (Llnumber >= 7 && Llnumber < 11) {
                txtLlSafeMoney.setText("Safe Money: $3200");
            }
        } else {
            txtLlSafeMoney.setText("Safe Money: $0");
        }
    }


    private void currentQuestion() {
        txtLlQuestionNum.setText(Llnumber + "/11");
    }

    // use this function to ipur data from Json file instead of database system.
    private void getQuestion(int number) {

        question = questionList.get(number-1);
        incorrect_answer = question.getIncorrect_answers();

//        set random range: 0<=random<4  {0,1,2,3}
        random = (int) (Math.random() * 4);
        incorrect_answer.add(random, question.getCorrect_answer());

        txtLlQuestion.setText(question.getQuestion());
        Llanswer1.setText(incorrect_answer.get(0));
        Llanswer2.setText(incorrect_answer.get(1));
        Llanswer3.setText(incorrect_answer.get(2));
        Llanswer4.setText(incorrect_answer.get(3));

    }

    private void setTimer() {

        timerDisplay = new Timer();
        timerFail = new Timer();

    }


    private TimerTask task1() {
        timerTask1 = new TimerTask() {
            @Override
            public void run() {
                txtLlTimer.setText("Time: " + String.valueOf(a) + "s");
                Log.d("timer", "Time: " + String.valueOf(a) + "s");
                a--;
            }
        };
        return timerTask1;
    }

    private TimerTask task2() {
        timerTask2 = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Lifelines.this, SummaryPage.class);
                intent.putExtra("complete", false);
                intent.putExtra("NumberComplete", Llnumber);
                Log.d("test", String.valueOf(Llnumber));
                Llnumber = 1;
                startActivity(intent);
            }
        };
        return timerTask2;
    }

    private void halffunction() {

/*        get correct answer index.
        random+1 :  randam could be 1,2,3,4;
        if oddeven = 0;  correct answer is 2,or 4, therefore, hide answer 1,3.
         if oddeven = 1;  correct answer is 1,or 3, therefore, hide answer 2,4.
*/
        oddOrEven = (random + 1) % 2;


        if (oddOrEven == 0) {

            Llanswer1.setClickable(false);
            Llanswer1.setVisibility(View.INVISIBLE);

            Llanswer3.setClickable(false);
            Llanswer3.setVisibility(View.INVISIBLE);

        }


    }

    private void resetAnswerVisiable() {

        Llanswer1.setVisibility(View.VISIBLE);
        Llanswer2.setVisibility(View.VISIBLE);
        Llanswer3.setVisibility(View.VISIBLE);
        Llanswer4.setVisibility(View.VISIBLE);

        Llanswer1.setClickable(true);
        Llanswer2.setClickable(true);
        Llanswer3.setClickable(true);
        Llanswer4.setClickable(true);

    }

    private void AudienceMethod() {

        int[] possibility = probability();
        int[] withoutSpecficPossiblility;
        int correctAnswer, currentPosition = 0;


//       easy Question.
        if (Llnumber <= 5) {

            correctAnswer = findMaximumNumberIndex(possibility);
            withoutSpecficPossiblility = withoutSpecficPossiblility(possibility, correctAnswer, 3);

//          use random+1 find which is correct answer in radio group.
//            and then use possibility[correctAnswer] to give maximum value to correct.
            changeDisplayByAudienceFunction(correctAnswer, possibility, withoutSpecficPossiblility);
        }
//        Medium and hard Question.
        else {

            correctAnswer = findMinimumNumberIndex(possibility);
            withoutSpecficPossiblility = withoutSpecficPossiblility(possibility, correctAnswer, 3);

            changeDisplayByAudienceFunction(correctAnswer, possibility, withoutSpecficPossiblility);
        }
    }

    private void switchQuestion(){

        if((Llnumber+11)>=questionList.size()){
            displayQuestion(questionList.size()-1);
        }
        else{
            displayQuestion(Llnumber+11);
        }
    }

//    use this method to get new int arraylist without specfic inedx value, number represent the new arraylist size.
    private int[] withoutSpecficPossiblility(int[] intlist, int index,int number){
        int[] returnList = new int[number];

        for(int i =0,y=0; i<intlist.length;i++){
            if(i!=index){
                returnList[y] =intlist[i];
                y++;
            }
        }
        return returnList;
    }

//  this method is return the index of maximum value of int arraylist;
    private int findMaximumNumberIndex(int[] list){
        int maximum =0;

        for(int i=0;i< list.length;i++ ){
            if(list[i]>=list[maximum]){
                maximum=i;
            }

        }
        return maximum;
    }

    //  this method is return the index of minimum value of int arraylist;
    private int findMinimumNumberIndex(int[] list){
        int minimum =0;

        for(int i=0;i< list.length;i++ ){
            if(list[i]<=list[minimum]){
                minimum =i;
            }
        }
        return minimum;
    }

    private void changeDisplayByAudienceFunction(int correctAnswer, int[] possibility, int[] withoutSpecficPossiblility) {
        switch ((random + 1)) {
            case 1:
                Llanswer1.setText(incorrect_answer.get(0) + " " + possibility[correctAnswer] + "%");
                Llanswer2.setText(incorrect_answer.get(1) + " " + withoutSpecficPossiblility[0] + "%");
                Llanswer3.setText(incorrect_answer.get(2) + " " + withoutSpecficPossiblility[1] + "%");
                Llanswer4.setText(incorrect_answer.get(3) + " " + withoutSpecficPossiblility[2] + "%");
                break;
            case 2:
                Llanswer1.setText(incorrect_answer.get(0) + " " + withoutSpecficPossiblility[0] + "%");
                Llanswer2.setText(incorrect_answer.get(1) + " " + possibility[correctAnswer] + "%");
                Llanswer3.setText(incorrect_answer.get(2) + " " + withoutSpecficPossiblility[1] + "%");
                Llanswer4.setText(incorrect_answer.get(3) + " " + withoutSpecficPossiblility[2] + "%");
                break;
            case 3:
                Llanswer1.setText(incorrect_answer.get(0) + " " + withoutSpecficPossiblility[0] + "%");
                Llanswer2.setText(incorrect_answer.get(1) + " " + withoutSpecficPossiblility[1] + "%");
                Llanswer3.setText(incorrect_answer.get(2) + " " + possibility[correctAnswer] + "%");
                Llanswer4.setText(incorrect_answer.get(3) + " " + withoutSpecficPossiblility[2] + "%");
                break;
            case 4:
                Llanswer1.setText(incorrect_answer.get(0) + " " + withoutSpecficPossiblility[0] + "%");
                Llanswer2.setText(incorrect_answer.get(1) + " " + withoutSpecficPossiblility[1] + "%");
                Llanswer3.setText(incorrect_answer.get(2) + " " + withoutSpecficPossiblility[2] + "%");
                Llanswer4.setText(incorrect_answer.get(3) + " " + possibility[correctAnswer] + "%");
                break;
        }
    }

    private int[] probability(){
        int [] returnlist = new int[4];

        int a,b,c,d;

        a = (int)(Math.random()*100);

        b=(int)(Math.random()*(100-a));

        c =(int)(Math.random()*(100-a-b));

        d = 100-a-b-c;

        returnlist[0]=a;
        returnlist[1]=b;
        returnlist[2]=c;
        returnlist[3]=d;

        return returnlist;
    }
}