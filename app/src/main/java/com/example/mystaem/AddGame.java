package com.example.mystaem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class AddGame extends AppCompatActivity {

    private EditText addprice, addgamename,addkind,addgamepic;
    private Button submit;
    private Button btnback;



    private FirebaseDatabase db = null;
    private FirebaseAuth auth = null;
    private DatabaseReference ref = null;
    private StorageReference storageReference = null;
    private ProgressDialog prg = null;




    private static final String TAG = "Update";
    private String date;
    private DatePickerDialog.OnDateSetListener mDataSetListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);



        btnback = findViewById(R.id.btnback);


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddGame.this,MainActivity.class));
            }
        });

      //  addgamepic = findViewById(R.id.addgamepic);

        addkind = findViewById(R.id.addkind);
        addgamename = findViewById(R.id.addgamename);
        addprice = findViewById(R.id.addprice);
        submit = findViewById(R.id.submit);



        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ref = db.getReference("Games");
        storageReference = FirebaseStorage.getInstance().getReference();




        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Games").push();

                    String gamecode = reference.getKey();
                    String kind = addkind.getText().toString();
                    String name = addgamename.getText().toString();
                    String price = addprice.getText().toString();

                  //  String pic = addgamepic.getText().toString();

                    Games Games = new Games(gamecode, kind, name, price,true);
                    Games.setGamecode(gamecode);


                    ref.child(gamecode).setValue(Games).addOnCompleteListener(AddGame.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddGame.this, "Game added", Toast.LENGTH_SHORT).show();

                                addkind.setText("");
                                addgamename.setText("");
                                addprice.setText("");

                              //  addgamepic.setText("");

                            }
                            else{
                                Toast.makeText(AddGame.this, "didnt work", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }



        });
    }

}