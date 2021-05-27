package com.example.mystaem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewList extends ArrayAdapter<Review> {

    private Activity context;
    private List<Review> reviewlist;

    private DatabaseReference ref2=null;


    public ReviewList(Activity context, List<Review> reviewlist){
        super(context, R.layout.review_card, reviewlist);
        this.context = context;
        this.reviewlist = reviewlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        final View listViewItem = inflater.inflate(R.layout.review_card, null, true);

        final TextView txtusername = listViewItem.findViewById(R.id.txtusername);
        final TextView txtcomm = listViewItem.findViewById(R.id.txtcomm);


        final Review subject = reviewlist.get(position);


       txtusername.setText(subject.getUsername());
        txtcomm.setText(subject.getComment());






        return listViewItem;
    }
}
