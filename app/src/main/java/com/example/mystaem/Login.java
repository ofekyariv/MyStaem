package com.example.mystaem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener {
    //------------------------
    //Global Vars Deceleration
    //------------------------

    EditText edpass, edemail;
    Button btnLogin, btnNeedReg;

    //firebase
    private FirebaseAuth auth = null;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference reference = null;
    private DatabaseReference cartreference = null;

    //------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //assignment
        auth = FirebaseAuth.getInstance();
        reference = db.getReference("User");
        cartreference = db.getReference("Cart");

        edpass = findViewById(R.id.login_input_password);
        edemail = findViewById(R.id.login_input_email);

        btnLogin = findViewById(R.id.login_btn_login);
        btnLogin.setOnClickListener(this);

        btnNeedReg = findViewById(R.id.btnNeedReg);
        btnNeedReg.setOnClickListener(this);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                final String email=edemail.getText().toString();
                final String pass=edpass.getText().toString();

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login.this, "You are in", Toast.LENGTH_SHORT).show();

                            Global.setUid(auth.getUid());

                            reference=reference.child(auth.getUid());

                            reference.addListenerForSingleValueEvent(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    Global.setUser(dataSnapshot.getValue(User.class));
                                    final User user = dataSnapshot.getValue(User.class);
                                    Global.setUser(user);

                                    cartreference.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                                                if(postSnapshot.child("userId").getValue().equals(auth.getUid())){
                                                    Global.setCart(snapshot.getValue(Cart.class));
                                                    Global.getCart().setCartCode(postSnapshot.getKey());
                                                }
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                    startActivity(new Intent(Login.this,MainActivity.class));

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError)
                                {
                                    Toast.makeText(Login.this, "ooops", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

    }



    @Override
    public void onClick(View view) {
        switch ( view.getId()){
            case R.id.btnNeedReg:


                    Intent i = new Intent(this,Reg.class);
                    startActivity(i);
                 //sends to the reg page;
                break;
        }

    }


}