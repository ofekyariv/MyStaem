package com.example.mystaem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class UpdateGame extends AppCompatActivity {


    private TextView currentprice,price;
    private EditText editprice ,editpic;
    private Button submitprice ,disable,submiturl;


    private Button btnback;

    private FirebaseDatabase db=null;
    private FirebaseAuth auth = null;
    private DatabaseReference ref=null;
    private StorageReference storageReference=null;


    private static final String TAG="Update";
    private String date;
    private DatePickerDialog.OnDateSetListener mDataSetListener;


    DatabaseReference databaseProduct;
    ListView listViewProduct;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_game);


        editprice = findViewById(R.id.editprice);
        editpic = findViewById(R.id.editpic);
        submitprice = findViewById(R.id.submitprice);
        submiturl = findViewById(R.id.submiturl);
        disable = findViewById(R.id.disable);


        price.setText(Global.getGame().getPrice());


        btnback = findViewById(R.id.btnback);


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateGame.this,MainActivity.class));
            }
        });





        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ref = db.getReference("Games");
        storageReference = FirebaseStorage.getInstance().getReference();




        submitprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(Global.getGame().getGamecode()).child("price").setValue(editprice.getText().toString());
                startActivity(new Intent(UpdateGame.this,MainActivity.class));
            }

        });


        submiturl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child(Global.getGame().getGamecode()).child("pic").setValue(editpic.getText().toString());
                startActivity(new Intent(UpdateGame.this,MainActivity.class));
            }

        });


        disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Global.getGame().setAvilable(false);
                startActivity(new Intent(UpdateGame.this,MainActivity.class));
            }

        });



    }


}