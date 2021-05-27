package com.example.mystaem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class GameInfo extends AppCompatActivity {

    private FirebaseDatabase db=null;
    private FirebaseAuth auth = null;
    private DatabaseReference ref=null;
    private DatabaseReference ref2=null;
    private DatabaseReference rev =null;
    private StorageReference storageReference=null;
    private ProgressDialog prg =null;

    private Button btnback;
    private ImageView gamepic;
    private TextView txtname,txtprice,likes,dislikes;
    private Button  btnupdate,btnadd;

    private String gamecode =Global.getGame().getGamecode();

    ListView listViewProduct;
    List<Review> reviewList;





    @Override
    protected void onStart() {
        super.onStart();



        rev.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                reviewList.clear();

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Review product = productSnapshot.getValue(Review.class);


                    reviewList.add(product);


                }

                ReviewList adapter = new ReviewList(GameInfo.this, reviewList);
                listViewProduct.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GameInfo.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);


        btnback = findViewById(R.id.btnback);


        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameInfo.this,MainActivity.class));
            }
        });


        Games games = Global.getGame();

        txtname = findViewById(R.id.txtname);
        likes = findViewById(R.id.likes);
        dislikes = findViewById(R.id.dislikes);
        txtprice = findViewById(R.id.txtprice);
        btnupdate= findViewById(R.id.btnupdate);
        btnadd= findViewById(R.id.btnadd);
        gamepic=findViewById(R.id.gamepic);

        gamecode =Global.getGame().getGamecode();


        listViewProduct = (ListView) findViewById(R.id.lst);



        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ref = db.getReference("Games");
        ref2 = db.getReference("Cart");
        rev = db.getReference("Review").child(gamecode);
        storageReference = FirebaseStorage.getInstance().getReference();

        reviewList = new ArrayList<>();

        String numoflikes = String.valueOf(Global.getGame().getLikes());
        String numofdislikes = String.valueOf(Global.getGame().getDislikes());


        likes.setText(numoflikes);
        dislikes.setText(numofdislikes);
        txtprice.setText(Global.getGame().getPrice());
        txtname.setText(Global.getGame().getName());
        String picture = Global.getGame().getPic();

        //you have to get the part of the link 0B9nFwumYtUw9Q05WNlhlM2lqNzQ
        String[] p=picture.split("/");
        //Create the new image link
        String imageLink="https://drive.google.com/uc?export=download&id="+p[5];
        Picasso.get().load(imageLink).into(gamepic);







        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.getUser().getType().equals("Admin"))
                {
                    startActivity(new Intent(GameInfo.this,UpdateGame.class));
                }

                else
                    Toast.makeText(GameInfo.this,"you are not an admin", Toast.LENGTH_LONG).show();

            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addingToCartList();

                Toast.makeText(GameInfo.this,"Game added to your cart",Toast.LENGTH_LONG).show();
            }

                    




        });

    }
    private  void  addingToCartList()
    {
        Global.getCart().AddProduct(Global.getGame());
        String check = Global.getCart().getCartCode();
        ref2.child(check).child("myGames").child(Global.getGame().getGamecode()).setValue(Global.getGame());


    }

}

