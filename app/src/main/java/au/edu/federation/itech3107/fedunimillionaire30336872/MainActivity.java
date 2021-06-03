package au.edu.federation.itech3107.fedunimillionaire30336872;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.PublicKey;
import java.util.List;

import DataBase.AppDatabase;
import DataBase.Entity.QuestionEntity;
import DataBase.QuestionDao;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private Button millionaire, hotSeat, lifelines;
    private SensorManager sensorManager;
    private Sensor  sensor;
    private int xAxisRight=0, xAxisLeft=0;


    AppDatabase appDatabase;
    QuestionDao questionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this,sensor,sensorManager.SENSOR_DELAY_NORMAL);



        appDatabase = AppDatabase.getDatabase(this.getApplicationContext());
        questionDao = appDatabase.getQuestionDao();

        findId();
        setListeners();


////        if need, call the method below, check instruction of the method.
//            setExampleData();


    }

            ////    test use json file
//    public OnlineQuestionEntity test1(){
//
//        String  test = "{\"category\":\"Politics\",\"type\":\"multiple\",\"difficulty\":\"medium\",\"question\":\"Which president erased the national debt of the United States?\",\"correct_answer\":\"Andrew Jackson\",\"incorrect_answers\":[\"Ronald Reagan\",\"John F. Kennedy\",\"Franklin Roosevelt\"]}";
//
//        Gson gson = new Gson();
//
//        OnlineQuestionEntity qe1 = gson.fromJson(test,OnlineQuestionEntity.class);
//
//        return qe1;
//    }

    /*
       below part is test for Gson and json
       and pick up data form database to json file.
      */

   //    private void writeJson(){
//        Gson gson = new Gson();
//        QuestionEntity test = questionDao.selectSpecificQuestion(1);
//        String json = gson.toJson(test);
//    }
//
//
//    private void readFromJson(){
//        Gson gson = new Gson();
//        String json = "{\"answer1\":\"Humanity\",\"answer2\":\"Health\",\"answer3\":\"Honour\",\"answer4\":\"Household\",\"correctAnswer\":2,\"id\":1,\"question\":\"In the UK, the abbreviation NHS stands for National what Service?\"}";
//        QuestionEntity test1 = gson.fromJson(json,QuestionEntity.class);
//    }

    /*Find the button for different function;
    millionaire is for assignment 1 function,
    hot seat is for assignment 2 function;
    */
    private void findId(){
        millionaire = findViewById(R.id.game);
        hotSeat = findViewById(R.id.hotSeat);
        lifelines = findViewById(R.id.lifelines);
    }


    private void setListeners(){
        OnClick onClick = new OnClick();
        millionaire.setOnClickListener(onClick);
        hotSeat.setOnClickListener(onClick);
        lifelines.setOnClickListener(onClick);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.values[0]>=5){
            xAxisRight++;
        }
        if(event.values[0]<=-5){
            xAxisLeft++;
        }

        if((xAxisRight>=2) && (xAxisLeft>=2)){
            Intent intent = new Intent(MainActivity.this,SensorPage.class);
            xAxisRight=0;
            xAxisLeft=0;
            startActivity(intent);

        }

//
//        Log.d("sensor test","x:"+event.values[0]+", y: "+event.values[1]+", Z:"+event.values[2]);



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private class OnClick implements View.OnClickListener{
        Intent intent;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.game:
                    intent = new Intent(MainActivity.this,game1.class);
                    break;
                case R.id.hotSeat:
                    intent = new Intent(MainActivity.this,HotSeat.class);
                    break;
                case R.id.lifelines:
                    intent = new Intent(MainActivity.this,Lifelines.class);
                    break;
            }
            startActivity(intent);
        }
    };

    /*Notice: this function is for input example Question if need, however please remind to remove this method at onCreate method.
              if there is ready some question in the database, don't use this.
              if use this method, remind to remove method and restart the Emulator to avoid input data again, it will cause problem.
    */


    private void setExampleData(){

        // get resource.
        Resources res = getResources();

        //Example data.
        QuestionEntity question1 = new QuestionEntity("In the UK, the abbreviation NHS stands for National what Service?",
                                                        "Humanity",
                                                        "Health",
                                                        "Honour",
                                                        "Household",
                                                        2);

        QuestionEntity question2 = new QuestionEntity("Which Disney character famously leaves a glass slipper behind at a royal ball?",
                "Pocahontas",
                "Sleeping Beauty",
                "Cinderella",
                "Elsa",
                3);

        QuestionEntity question3 = new QuestionEntity("What name is given to the revolving belt machinery in an airport that delivers checked luggage from the plane to baggage reclaim?",
                "Hangar",
                "Terminal",
                "Concourse",
                "Carousel",
                4);

        QuestionEntity question4 = new QuestionEntity("Which of these brands was chiefly associated with the manufacture of household locks?",
                "Phillips",
                "Flymo",
                "Chubb",
                "Ronseal",
                3);

        QuestionEntity question5 = new QuestionEntity("The hammer and sickle is one of the most recognisable symbols of which political ideology?",
                "Republicanism",
                "Communism",
                "Conservatism",
                " Liberalism",
                2);

        QuestionEntity question6 = new QuestionEntity("Which toys have been marketed with the phrase “robots in disguise”?",
                "Bratz Dolls",
                "Sylvanian Families",
                "Hatchimals",
                "Transformers",
                4);

        QuestionEntity question7 = new QuestionEntity("What does the word loquacious mean?",
                "Angry",
                "Chatty",
                "Beautiful",
                "Shy",
                2);

        QuestionEntity question8 = new QuestionEntity("Obstetrics is a branch of medicine particularly concerned with what?",
                "Childbirth",
                "Broken bones",
                "Heart conditions",
                "Old age",
                1);

        QuestionEntity question9 = new QuestionEntity("In Doctor Who, what was the signature look of the fourth Doctor, as portrayed by Tom Baker?",
                "Bow-tie, braces and tweed jacket",
                "Wide-brimmed hat and extra long scarf",
                "Pinstripe suit and trainers",
                "Cape, velvet jacket and frilly shirt",
                2);

        QuestionEntity question10 = new QuestionEntity("Which of these religious observances lasts for the shortest period of time during the calendar year?",
                "Ramadan",
                "Diwali",
                "Lent",
                "Hanukkah",
                2);

        QuestionEntity question11 = new QuestionEntity("At the closest point, which island group is only 50 miles south-east of the coast of Florida?",
                "Bahamas",
                "US Virgin Islands",
                "Turks and Caicos Islands",
                "Bahamas",
                1);

        QuestionEntity question12 = new QuestionEntity("Construction of which of these famous landmarks was completed first?",
                "Empire State Building",
                "Royal Albert Hall",
                "Eiffel Tower",
                "Big Ben Clock Tower",
                4);

        QuestionEntity question13 = new QuestionEntity("Which of these cetaceans is classified as a “toothed whale”?\n",
                "Gray whale",
                "Minke whale",
                "Sperm whale",
                "Humpback whale",
                3);

        QuestionEntity question14 = new QuestionEntity("Who is the only British politician to have held all four “Great Offices of State” at some point during their career?",
                "David Lloyd George",
                "Harold Wilson",
                "James Callaghan",
                "John Major",
                3);

        QuestionEntity question15 = new QuestionEntity("In 1718, which pirate died in battle off the coast of what is now North Carolina?",
                "Calico Jack",
                "Blackbeard",
                "Bartholomew Roberts",
                "Captain Kidd",
                2);

        //insert data.
        questionDao.insertQuestion(question1,question2,question3,question4,question5,question6,question7,question8,question9,question10,question11,question12,question13,question14,question15);
    }

}

