package com.example.mystaem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoughtCarts extends AppCompatActivity {

    Button back;
    DatabaseReference dbRefCartGames;
    List<Games> gamesList;
    ListView BoughtGamesListView;
    private FirebaseDatabase db=null;
    private DatabaseReference ref2=null;
    String userEmail="";
    public static String cartCode="";


    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            userEmail = extras.getString("userEmail");
        }
        Log.d("TRy", Global.getCart().getCartCode());
        dbRefCartGames.child(("Cart")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    String dbemail = productSnapshot.child("email").getValue().toString();
                    if(userEmail.equals(""))
                    {
                        userEmail = Global.getUser().getEmail();
                    }
                    String dbbought = productSnapshot.child("bought").getValue().toString();
                    if (dbemail.equals(userEmail) && dbbought.equals("true")){
                        cartCode = productSnapshot.getKey();
                        for(DataSnapshot gamesSnapshot : productSnapshot.child("myGames").getChildren()){
                            Games games = gamesSnapshot.getValue(Games.class);

                            gamesList.add(games);
                        }

                    }
                }
                Log.d("check ", gamesList.toString());

                BoughtGamesList adapter = new BoughtGamesList(BoughtCarts.this,gamesList);
                BoughtGamesListView.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BoughtCarts.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bought_carts);

        db = FirebaseDatabase.getInstance();
        dbRefCartGames = FirebaseDatabase.getInstance().getReference();
        gamesList = new ArrayList<Games>();
        BoughtGamesListView = (ListView) findViewById(R.id.lst);
        back= findViewById(R.id.back);

        ref2 = db.getReference("Cart");

        //on long click
        BoughtGamesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BoughtCarts.this,MainActivity.class));
            }
        });
    }
}
