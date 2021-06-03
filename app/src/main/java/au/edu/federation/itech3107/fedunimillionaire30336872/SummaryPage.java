package au.edu.federation.itech3107.fedunimillionaire30336872;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import DataBase.AppDatabase;
import DataBase.Entity.UserEntity;
import DataBase.UserEntityDao;

public class SummaryPage extends AppCompatActivity {

    private TextView summaryInform1;
    private Button goBack, saveAndReturn;
    private boolean complete, locationresult=false;
    private int number;
    private EditText name;
    private String nameInput;
    private Long date;
    private double latitude, longitude;
    private Calendar calendar;
    private FusedLocationProviderClient fusedLocationProviderClient;

    AppDatabase appDatabase;
    UserEntityDao userEntityDao;
    UserEntity userEntity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        complete = bundle.getBoolean("complete");
        // check how many question answered.
        if (!complete) {
            Log.d("test", String.valueOf(complete));
            number = bundle.getInt("NumberComplete");
            Log.d("test", String.valueOf(number));
        }
        setContentView(R.layout.activity_summary_page);

        appDatabase = AppDatabase.getDatabase(this.getApplicationContext());
        userEntityDao = appDatabase.getUserEntityDao();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        findId();
        onClick();


        setInformation();

    }


    private void onClick() {
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SummaryPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        saveAndReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getName();
                if (nameInput == null) {
                    Toast.makeText(SummaryPage.this, "Please input Name", Toast.LENGTH_LONG).show();
                } else {

                    getLocation();
                    if (locationresult) {

                        userEntity = new UserEntity(nameInput);
                        userEntity.setQuestionFinished(questionFinished());
                        userEntity.setReward(reward(number));
                        calendar = Calendar.getInstance();
                        userEntity.setDate(calendar.getTime().getTime());
                        userEntity.setLatitude(latitude);
                        userEntity.setLongitude(longitude);
                        userEntityDao.insertUser(userEntity);

                        Intent intent = new Intent(SummaryPage.this, MainActivity.class);
                        startActivity(intent);

                    }else {
                        Toast.makeText(SummaryPage.this, "Cannot get location ", Toast.LENGTH_LONG).show();
                    }


                }
            }
        });
    }


    private void getName() {
        nameInput = name.getText().toString();
    }


    private void setInformation() {
        if (!complete) {
            if (number > 1) {
                if (number >= 2 && number < 7) {
                    summaryInform1.setText("$1000");
                }
                if (number >= 7 && number < 11) {
                    summaryInform1.setText("$3200");
                }
            } else {
                summaryInform1.setText("$0");
            }
        } else {
            summaryInform1.setText("$ 1M");
        }
    }

    private void findId() {
        summaryInform1 = findViewById(R.id.summaryInform1);
        goBack = findViewById(R.id.summaryBack);
        saveAndReturn = findViewById(R.id.btnSave);
        name = findViewById(R.id.txtInputName);
    }



    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SummaryPage.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location;
                location = task.getResult();
                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(SummaryPage.this, Locale.getDefault());
                        List<Address> address = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        latitude = address.get(0).getLatitude();
                        longitude = address.get(0).getLongitude();
                        locationresult=true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private int questionFinished(){
        int result;
        if(complete){
            result=11;
        }
        else {
            result=number-1;
        }
        return result;
    }

    private int reward(int number) {
        int result = 0;
        switch (number) {
            case 1:
                result=0;
                break;
            case 2:
                result=1000;
                break;
            case 3:
                result=2000;
                break;
            case 4:
                result=4000;
                break;
            case 5:
                result=8000;
                break;
            case 6:
                result=16000;
                break;
            case 7:
                result=32000;
                break;
            case 8:
                result=64000;
                break;
            case 9:
                result=125000;
                break;
            case 10:
                result=250000;
                break;
            case 11:
                result=500000;
                break;
        }
        return result;
    }

}