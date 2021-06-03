package DataBase;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import DataBase.Entity.QuestionEntity;

@Dao
public interface QuestionDao {

    @Insert
    void insertQuestion(QuestionEntity...userEntities);

    @Update
    void updateQuestion(QuestionEntity...userEntities);

    @Delete
    void deleteQuestion(QuestionEntity...userEntities);


    @Query("SELECT * FROM QuestionEntity WHERE id = :id ORDER BY id ASC")
    QuestionEntity selectSpecificQuestion(long id);

    @Query("DELETE FROM QuestionEntity")
    void deleteAllQuestion();

    @Query("SELECT * FROM QuestionEntity ORDER BY id ASC")
    List<QuestionEntity> selectAllQuestion();
}
