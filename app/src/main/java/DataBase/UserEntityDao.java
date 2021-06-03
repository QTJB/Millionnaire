package DataBase;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import DataBase.Entity.UserEntity;

@Dao
public interface UserEntityDao {

    @Insert
    void insertUser(UserEntity...userEntity);

    @Update
    void updateUser(UserEntity...userEntities);

    @Delete
    void deleteUser(UserEntity...userEntities);

    @Query("SELECT * FROM UserEntity ORDER BY id ASC")
    List<UserEntity> selectAllUser();

    @Query("DELETE  FROM UserEntity")
    void deleteAllUser();

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    UserEntity SelectSpecficUser(long id);

    @Query("SELECT Latitude from UserEntity where id = :id")
    double selectSpecficLatitude(long id);

    @Query("SELECT Longitude from UserEntity where id = :id")
    double selectSpecficLongitude(long id);

    @Query("SELECT * FROM UserEntity ORDER BY QuestionFinished ASC")
    List<UserEntity> selectAllUserOrderByQuestionFinieshedAsc();

    @Query("SELECT * FROM UserEntity ORDER BY QuestionFinished DESC")
    List<UserEntity> selectAllUserOrderByQuestionFinieshedDESC();

    @Query("SELECT * FROM UserEntity ORDER BY Reward ASC")
    List<UserEntity> selectAllUserOrderByRewardAsc();

    @Query("SELECT * FROM UserEntity ORDER BY Reward Desc")
    List<UserEntity> selectAllUserOrderByRewardDESC();

    @Query("SELECT * FROM UserEntity ORDER BY Date ASC")
    List<UserEntity> selectAllUserOrderByDateAsc();

    @Query("SELECT * FROM UserEntity ORDER BY Date DESC")
    List<UserEntity> selectAllUserOrderByDateDesc();
}
