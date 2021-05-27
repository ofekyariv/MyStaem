package com.example.mystaem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Activity;
import android.app.DownloadManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


import java.util.List;

public class CartList extends ArrayAdapter<Games> {




        private Activity context;
        private List<Games> gamesList;


        public CartList(Activity context, List<Games> gamesList){
            super(context, R.layout.gamescardview, gamesList);
            this.context = context;
            this.gamesList = gamesList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            final View listViewItem = inflater.inflate(R.layout.gamescartview, null, true);



            final TextView txtname = (TextView) listViewItem.findViewById(R.id.txtname);
            final TextView txtprice = (TextView) listViewItem.findViewById(R.id.txtprice);




            final Games subject = gamesList.get(position);

            txtname.setText(subject.getName());
            txtprice.setText(subject.getPrice());





            return listViewItem;
        }
    }



