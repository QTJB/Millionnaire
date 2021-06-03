package DataBase.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class UserEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(name = "Name")
    private String name;

    @ColumnInfo(name="QuestionFinished")
    private int questionFinished;

    @ColumnInfo(name = "Reward")
    private int reward;

    @ColumnInfo(name= "Date")
    private long date;

    @ColumnInfo(name = "Latitude")
    private double latitude;

    @ColumnInfo(name = "Longitude")
    private double longitude;

    @Ignore
    public UserEntity(String name) {
        this.name = name;
    }

    public UserEntity(String name, int questionFinished, int reward, long date, double latitude, double longitude) {
        this.name = name;
        this.questionFinished = questionFinished;
        this.reward = reward;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuestionFinished() {
        return questionFinished;
    }

    public void setQuestionFinished(int questionFinished) {
        this.questionFinished = questionFinished;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
