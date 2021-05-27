package com.example.mystaem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.mystaem.BoughtCarts.cartCode;


public class BoughtGamesList extends ArrayAdapter<Games> {
        Games game;
        private Activity context;
        private List<Games> gamesList;
        DatabaseReference databaseReference;
        DatabaseReference databaseReference2;
        Double numVotes= 0.0;
        String gamecode = "";
        public BoughtGamesList(Activity context, List<Games> gamesList){
            super(context, R.layout.bought_games_cardview, gamesList);
            this.context = context;
            this.gamesList = gamesList;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            final View listViewItem = inflater.inflate(R.layout.bought_games_cardview, null, true);
            final TextView txtname = listViewItem.findViewById(R.id.txtname);
            final TextView txtprice = listViewItem.findViewById(R.id.txtprice);
            final ImageButton like = listViewItem.findViewById(R.id.like);
            final ImageButton dislike = listViewItem.findViewById(R.id.dislike);
            final Button btnsubmit = listViewItem.findViewById(R.id.btnsubmit);
            final EditText etreview = listViewItem.findViewById(R.id.etreview);
            game = gamesList.get(position);
            gamecode = game.getGamecode();
            txtname.setText(game.getName());
            txtprice.setText(game.getPrice());
            databaseReference2 = FirebaseDatabase.getInstance().getReference("Cart");
            databaseReference = FirebaseDatabase.getInstance().getReference("Games");



            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!etreview.getText().toString().equals("")) {
                        game = gamesList.get(position);
                        gamecode = game.getGamecode();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Review").child(gamecode).push();

                        //String revcode = reference.getKey();

                        Review review = new Review(gamecode, Global.getUser().username, etreview.getText().toString());


                        assert review != null;
                        reference.setValue(review);

                    }
                    else {
                        Toast.makeText(context, "pls add a comment", Toast.LENGTH_SHORT).show();
                    }
                }
            });



            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!game.getRated()) {

                        databaseReference.child(gamecode).child("likes").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                numVotes = dataSnapshot.getValue(Double.class);
                                databaseReference.child(gamecode).child("likes").setValue(numVotes + 1);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                        databaseReference2.child(cartCode).child("myGames").child(game.getGamecode()).child("rated").setValue(true);
                        like.setImageDrawable(convertView.getResources().getDrawable(R.drawable.like_a_press));
                        dislike.setEnabled(false);
                        like.setEnabled(false);
                    }
                }
            });
            dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!game.getRated())
                    {
                        databaseReference.child(gamecode).child("dislikes").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                numVotes = dataSnapshot.getValue(Double.class);
                                databaseReference.child(gamecode).child("dislikes").setValue(numVotes+1);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        databaseReference2.child(cartCode).child("myGames").child(game.getGamecode()).child("rated").setValue(true);
                        dislike.setImageDrawable(convertView.getResources().getDrawable(R.drawable.dislike_a_press));
                        dislike.setEnabled(false);
                        like.setEnabled(false);
                    }
                }
            });

            return listViewItem;
        }
    }

