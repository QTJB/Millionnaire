package DataBase;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import DataBase.Entity.QuestionEntity;
import DataBase.Entity.UserEntity;

@Database(entities = {QuestionEntity.class, UserEntity.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract QuestionDao getQuestionDao();
    public abstract UserEntityDao getUserEntityDao();

    private static AppDatabase INSTANCE;

    public static synchronized AppDatabase getDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class,"Application_Database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }


}
