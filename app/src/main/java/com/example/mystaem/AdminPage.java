 package com.example.mystaem;

 import androidx.appcompat.app.AppCompatActivity;

 import android.content.Intent;
 import android.os.Bundle;
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

 import com.example.mystaem.R;

 public class AdminPage extends AppCompatActivity {

     LinearLayout back;
     DatabaseReference databaseReference;

     ListView usersListView;

     List<User> userList;

     SearchView searchView;
     Button btnback;


     @Override
     protected void onStart() {
         super.onStart();
         databaseReference.child("User").addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 userList.clear();

                 for (DataSnapshot productSnapshot : dataSnapshot.getChildren()) {
                     User user = productSnapshot.getValue(User.class);
                         userList.add(user);
                 }

                 UserList adapter = new UserList(AdminPage.this,userList);
                 usersListView.setAdapter(adapter);
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {
                 Toast.makeText(AdminPage.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
             }


         });


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

     private void search(String str){
         ArrayList<User> myList =new ArrayList<>();
         for(User object : userList){
             if(object.getUsername().toLowerCase().contains(str.toLowerCase())){
                 myList.add(object);
             }
         }
         UserList adapter = new UserList(AdminPage.this,myList);
         usersListView.setAdapter(adapter);
     }


     public void logout() {
         Intent i = new Intent(this, Login.class);
         startActivity(i);
         finish();
     }

     public void update() {
         Intent i = new Intent(this, Update.class);
         startActivity(i);
         finish();
     }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        back =findViewById(R.id.back);
        NavigationView drawerMenu = findViewById(R.id.drawer_navigation_view);
        usersListView = (ListView) findViewById(R.id.lst);
        btnback =findViewById(R.id.btnback);
        searchView = findViewById(R.id.SearchView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userList = new ArrayList<User>();


        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(AdminPage.this, BoughtCarts.class);
                i.putExtra("userEmail",userList.get(position).getEmail());
                startActivity(i);
                finish();
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminPage.this,MainActivity.class));
            }
        });

    }
}