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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.mystaem.MainActivity.cart;

public class MyCart extends AppCompatActivity {

    Button  buy,del,back;
    DatabaseReference dbRefCartGames;
    List<Games> gamesList;
    ListView listViewProduct;
    TextView txtfinalprice;
    private FirebaseDatabase db=null;
    private DatabaseReference ref2=null;

    @Override
    protected void onStart() {
        super.onStart();

        Log.d("TRy", Global.getCart().getCartCode());
        dbRefCartGames.child(("Cart/" + Global.getCart().getCartCode() + "/myGames")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {

                    Games games = productSnapshot.getValue(Games.class);

                    gamesList.add(games);

                }
                Log.d("check ", gamesList.toString());

                GamesList adapter = new GamesList(MyCart.this,gamesList);
                listViewProduct.setAdapter(adapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyCart.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        db = FirebaseDatabase.getInstance();
        dbRefCartGames = FirebaseDatabase.getInstance().getReference();
        gamesList = new ArrayList<Games>();
        listViewProduct = (ListView) findViewById(R.id.lst);
        del= findViewById(R.id.del);
        buy= findViewById(R.id.buy);
        back= findViewById(R.id.back);
        ref2 = db.getReference("Cart");

        //on long click
        listViewProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return false;
            }
        });





        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DelCart();
                Toast.makeText(MyCart.this, "All games have been deleted from your cart", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MyCart.this,MainActivity.class));


            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BuyCart();
                Toast.makeText(MyCart.this, "All games have been Bought from your cart", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MyCart.this,MainActivity.class));


            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCart.this,MainActivity.class));
            }
        });





    }

    private  void  DelCart()
    {


        String check = Global.getCart().getCartCode();
        ref2.child(check).child("myGames").removeValue();

    }
    private  void  BuyCart()
    {

        String check = Global.getCart().getCartCode();
        ref2.child(check).child("bought").setValue(true);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Cart").push();

        String cartcode = reference.getKey();

        Cart cart = new Cart(false,Global.getUid(),Global.getUser().getEmail());
        cart.setCartCode(cartcode);


        assert cartcode != null;
        ref2.child(cartcode).setValue(cart);
    }
}




