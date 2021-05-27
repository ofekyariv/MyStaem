package com.example.mystaem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Update extends AppCompatActivity implements View.OnClickListener  {

    EditText name,firstname,lastname, phone ;
    TextView bdate;
    Button update, cancel;
    RadioButton male, female;
    DatePickerDialog dp;
    private String date;


    private static final String TAG="Register";

    private DatePickerDialog.OnDateSetListener mDataSetListener;


    DatabaseReference reference;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    User user = Global.getUser();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        reference = db.getReference("User");

        name = findViewById(R.id.update_user_name);
        firstname = findViewById(R.id.update_user_firstname);
        lastname = findViewById(R.id.update_user_Lastname);
        phone = findViewById(R.id.update_user_phone);
        bdate =findViewById(R.id.date);
        update = findViewById(R.id.update_user_update);
        cancel = findViewById(R.id.update_user_cancel);
        male = findViewById(R.id.update_user_gender_male);
        female = findViewById(R.id.update_user_gender_female);

        update.setOnClickListener(this);
        cancel.setOnClickListener(this);


        name.setText(user.getUsername());
        firstname.setText(user.getFirstname());
        lastname.setText(user.getLastname());
        phone.setText(user.getPhone());



        bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Update.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDataSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "onDataSet: mm/dd/yyy" + month + "/" + day + "/" + year );
                date = month+1 + "/" + day + "/" + year;
                bdate.setText(date);
            }
        };

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == cancel.getId()) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        if(v.getId() == update.getId()) {
            updateProcedure();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }


    }

    void updateProcedure() {
        if(!CheckInputs()) {return;}

        Global.loadingDialogShow(this);


        user.setBirth(date.toString());
        user.setUsername(name.getText().toString());
        user.setFirstname(firstname.getText().toString());
        user.setLastname(lastname.getText().toString());
        user.setPhone(phone.getText().toString());
        if(male.isChecked()) {user.setGender("Male"); } if(female.isChecked()) {user.setGender("Female"); }

        reference = reference.child(Global.getUid());
        reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Global.setUser(user);
                    finishUpdate(true);
                }
                else
                    finishUpdate(false);
            }
        });

    }
    void finishUpdate(boolean result) {
        Global.loadingDialogDismiss();
        if(result) {
            finish();
            Toast.makeText(this, "Successfully updated user's information", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "An error occurred while attempting to update user's information", Toast.LENGTH_SHORT).show();

    }

    public boolean CheckInputs() {
        //------------ Name check
        if (name.getText().toString().equals("")) {
            Toast.makeText(this, "Enter a Username", Toast.LENGTH_SHORT).show();
            return false;
        }
        //------------------------
        if (firstname.getText().toString().equals("")) {
            Toast.makeText(this, "Enter a First name", Toast.LENGTH_SHORT).show();
            return false;
        }
        //-------------------------
        if (lastname.getText().toString().equals("")) {
            Toast.makeText(this, "Enter a Last name", Toast.LENGTH_SHORT).show();
            return false;
        }
        //------------ Phone check
        if (phone.getText().toString().equals("")) {
            Toast.makeText(this, "Enter a phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        String abc = "absdefghijklmnopqrstuvwxyz";
        String phone_string = phone.getText().toString();
        for(int i = 0; i < abc.length(); i++) {
            if(phone_string.contains(String.valueOf(abc.toCharArray()[i]))) {
                Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        //------------------------


        //-------------------------

        return true;
    }
}
