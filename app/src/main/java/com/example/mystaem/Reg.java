package com.example.mystaem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;


public class Reg extends AppCompatActivity
implements View.OnClickListener {
    //------------------------
    //Global Vars Deceleration
    //------------------------

    private EditText inputEmail, inputPass, inputusername, inputPhone,inputfname,inputlname,inputregion,input_date;
    private Button btnReg, btnLogin;
    private RadioGroup rdg;
    private TextView txtdate;
    private CircleImageView pic;
    private ArrayList <String> list =new ArrayList<String>();
    private RadioButton rdbmale,rdbfemale;
    private String date;


    private static final String TAG="Register";

    private DatePickerDialog.OnDateSetListener mDataSetListener;
    //firebase stuff
    private FirebaseDatabase db=null;
    private FirebaseAuth auth = null;
    private DatabaseReference ref=null;
    private DatabaseReference ref2=null;
    private StorageReference storageReference=null;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Permission p = new Permission(this);
        p.verifyPermissions();

        //assignments
        inputEmail = findViewById(R.id.input_email);
        inputPass = findViewById(R.id.input_password);
        inputusername = findViewById(R.id.input_username);
        txtdate =findViewById(R.id.txtdate);
        inputfname = findViewById(R.id.input_fname);
        inputlname = findViewById(R.id.input_lname);
        inputPhone = findViewById(R.id.input_phone);
        rdg =findViewById(R.id.rdg);
        rdbfemale =findViewById(R.id.rdbfemale);
        rdbmale =findViewById(R.id.rdbmale);
        btnReg = findViewById(R.id.btn_reg);
        btnLogin = findViewById(R.id.btn_login);
        db = FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        ref=db.getReference("User");
        ref2=db.getReference("Cart");
        storageReference= FirebaseStorage.getInstance().getReference();


        //permissions shit

        //buttons clicklisteners
        btnReg.setOnClickListener( this);
        btnLogin.setOnClickListener(this);





      txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Reg.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDataSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(TAG, "onDataSet: mm/dd/yyy" + month + "/" + day + "/" + year );
                date = month+1 + "/" + day + "/" + year;
                txtdate.setText(date);
            }
        };





    }



    //------------------------------------------------------------------------------------------------------


    public void onClick(View v) {
        Toast.makeText(this, "wait a sec", Toast.LENGTH_SHORT).show();
        if (v.getId() == btnLogin.getId()) {
            Intent i = new Intent(this,Login.class);
            startActivity(i);
        } //sends to the Login page
        if(v.getId() == btnReg.getId()) { //BTN REGISTER!
            String email = inputEmail.getText().toString();
            String pass = inputPass.getText().toString();

            Task<AuthResult> res = auth.createUserWithEmailAndPassword(email, pass);

            res.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {


                        String email = inputEmail.getText().toString();
                        String pass = inputPass.getText().toString();
                        String username = inputusername.getText().toString();
                        String phone = inputPhone.getText().toString();
                        String lastname = inputlname.getText().toString();
                        String firstname = inputfname.getText().toString();
                     //   String region = inputregion.getText().toString();
                        String date = txtdate.getText().toString();



                        User user = new User(email,username,pass,phone,lastname,firstname,date);

                        Global.setUser(user);

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cart").push();

                        String cartcode = reference.getKey();

                        Cart cart = new Cart(false,auth.getUid(),email);
                        cart.setCartCode(cartcode);


                        ref2.child(cartcode).setValue(cart).addOnCompleteListener(Reg.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Reg.this, "Cart", Toast.LENGTH_SHORT).show();


                                }
                                else{
                                    Toast.makeText(Reg.this, "didnt work", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        ref.child(auth.getUid()).setValue(user).addOnCompleteListener(Reg.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(Reg.this, "you are in", Toast.LENGTH_SHORT).show();



                                    startActivity(new Intent(Reg.this, MainActivity.class));

                                }
                            }
                        });
                    }
                }
            });

            res.addOnFailureListener(this, new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Reg.this, "you have an account :(", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}