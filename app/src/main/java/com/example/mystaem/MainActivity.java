package com.example.mystaem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Image;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.service.autofill.VisibilitySetterAction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import androidx.annotation.NonNull;

import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout back;
    DatabaseReference dbRefGames;
    DatabaseReference dbRefCart;

    ListView listViewProduct;
    List<Cart> cartList;
    List<Games> gamesList;
    private WifiManager wifiManager;
    SearchView searchView;


    public static ArrayList<Games> cart = new ArrayList<>();



    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(wifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);

        dbRefGames.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                gamesList.clear();

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {

                    Games games = productSnapshot.getValue(Games.class);

                        gamesList.add(games);

                }

                GamesList adapter = new GamesList(MainActivity.this,gamesList);
                listViewProduct.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




        dbRefCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                    Cart cart = productSnapshot.getValue(Cart.class);

                    if (cart.getEmail().equals(Global.getUser().getEmail())&&cart.getBought().equals(false)) {

                        Global.setCart(cart);
                        Global.getCart().setCartCode(productSnapshot.getKey());

                    }
                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });
//------------------------------------------------------------------------------------------------------------------------

        if(searchView != null){
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    search(s);
                    return true;
                }
            });
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private void search(String str){
        ArrayList<Games> myList =new ArrayList<>();
        for(Games object : gamesList){
            if(object.getName().toLowerCase().contains(str.toLowerCase()))
            {
                myList.add(object);
            }
        }
        GamesList adapter = new GamesList(MainActivity.this,myList);
        listViewProduct.setAdapter(adapter);
    }

//------------------------------------------------------------------------------------------------------------------------
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStaeExtra =  intent.getIntExtra(wifiManager.EXTRA_WIFI_STATE, wifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStaeExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    break;

                case WifiManager.WIFI_STATE_DISABLED:
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            context);

                    // set title


                    // set dialog message

                    dialog alert = new dialog();
                    alert.showDialog(MainActivity.this, "Error wifi is turned off pleas turn it back on or you will not be able to buy from the app");

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();


                  /*  openDialog();
                    wifiManager.setWifiEnabled(true);
                   */
                    break;

            }
        }
    };


//------------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.drawer_menu_logout:// logout user
                logout();
                break;
                case R.id.drawer_menu_add_game:// add game to list
                Add();
                break;
                case R.id.drawer_menu_update_profile://update user profile
                update();
                break;
                case R.id.drawer_menu_settings://admin settings
                admin();
                break;
                case R.id.drawer_menu_cart: //cart
                mycart();
                break;
            case R.id.drawer_menu_owned: //cart
                boughtCarts();
                break;





        }
        return false;
    }
    public void logout() {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
    }
    public void Add() {
        if (Global.getUser().type.equals("Admin"))
        {
            Intent i = new Intent(this, AddGame.class);
            startActivity(i);
            finish();
        }
        else
            Toast.makeText(MainActivity.this,"you are not an admin", Toast.LENGTH_LONG).show();
    }
    public void admin() {
        if (Global.getUser().type.equals("Admin"))
        {
        Intent i = new Intent(this, AdminPage.class);
        startActivity(i);
        finish();
        }
        else
        Toast.makeText(MainActivity.this,"you are not an admin", Toast.LENGTH_LONG).show();
    }
    public void update() {
        Intent i = new Intent(this, Update.class);
        startActivity(i);
        finish();
    }
    public void boughtCarts() {
        Intent i = new Intent(this, BoughtCarts.class);
        startActivity(i);
        finish();
    }
    public void mycart() {//if admin go to all cart if user go to cart
        {
            Intent i = new Intent(this, MyCart.class);
            startActivity(i);
            finish();

        }

    }






//------------------------------------------------------------------------------------------------------------------------


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        back =findViewById(R.id.back);
        NavigationView drawerMenu = findViewById(R.id.drawer_navigation_view);
        drawerMenu.setNavigationItemSelectedListener(this);






        dbRefGames = FirebaseDatabase.getInstance().getReference().child("Games");

        dbRefCart = FirebaseDatabase.getInstance().getReference().child("Cart");

        listViewProduct = (ListView) findViewById(R.id.lst);

        gamesList = new ArrayList<>();

        searchView = findViewById(R.id.SearchView);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Global.setGame(gamesList.get(position));
                Intent i = new Intent(MainActivity.this, GameInfo.class);
                startActivity(i);
                finish();


            }
        });

    }

}

