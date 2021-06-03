package au.edu.federation.itech3107.fedunimillionaire30336872;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import DataBase.AppDatabase;
import DataBase.Entity.QuestionEntity;
import DataBase.QuestionDao;

public class GsonFunction {

    public static String readAllQuestionFromdatabasetoJson(QuestionDao questionDao) {

        Gson gson = new Gson();
        List<QuestionEntity> toJson = questionDao.selectAllQuestion();

        return GsonFunction.objectListtoJsonString(gson,toJson);
    }


    public static String objectListtoJsonString(Gson gson,List<QuestionEntity> list){

        int index = 0;
        QuestionEntity tempQuestion;
        String tempjson, json = "[";

        if(!list.isEmpty()){

            while(list.size()>index){
                if(index!=0){
                    json =json+",";
                }
                tempQuestion = list.get(index);
                tempjson = gson.toJson(tempQuestion);
                json = json + tempjson;
                index++;
                Log.d("Gsontest",String.valueOf(index));
            }
        }
        json = json+"]";



        return json;
    }


//    // to use this function, make sure the String is like   [Value1 , Value2, .......Value N] .
    public static List<OnlineQuestionEntity> JsonStringToObjectList (String jsonString){

        List<OnlineQuestionEntity> question;

        Gson gson = new Gson();

        Type onlineQuestion = new TypeToken<ArrayList<OnlineQuestionEntity>>(){}.getType();

        question = gson.fromJson(jsonString,onlineQuestion);

        return question;
    }


    // the file is text file and local at src\naib\Assets\SampleQuestion.txt
    public static List<OnlineQuestionEntity> getQuestionFromJsonForm(Context context){

        String test = "";

        try {
            InputStream is = context.getAssets().open("SampleQuestion.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            test = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }


        List<OnlineQuestionEntity> question = GsonFunction.JsonStringToObjectList(test);
//
        return question;
    }

}
