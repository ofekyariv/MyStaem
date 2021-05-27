package com.example.mystaem;

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

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class GamesList extends ArrayAdapter<Games> {

        private Activity context;
        private List<Games> gamesList;


        public GamesList(Activity context, List<Games> gamesList){
            super(context, R.layout.gamescardview, gamesList);
            this.context = context;
            this.gamesList = gamesList;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();

            final View listViewItem = inflater.inflate(R.layout.gamescardview, null, true);

            final TextView txtname = listViewItem.findViewById(R.id.txtname);
            final TextView txtkind = listViewItem.findViewById(R.id.txtkind);
            final TextView txtprice = listViewItem.findViewById(R.id.txtprice);
            final TextView txtavailable = listViewItem.findViewById(R.id.txtavailable);
            final ImageView img =  listViewItem.findViewById(R.id.pic);



            final Games game = gamesList.get(position);

            txtname.setText(game.getName());
            txtkind.setText(game.getKind());
            txtprice.setText(game.getPrice());
            txtavailable.setText(game.getAvilable().toString());
            String picture = game.getPic();

            //you have to get the part of the link 0B9nFwumYtUw9Q05WNlhlM2lqNzQ
            String[] p=picture.split("/");
            //Create the new image link
            String imageLink="https://drive.google.com/uc?export=download&id="+p[5];
            Picasso.get().load(imageLink).into(img);






            return listViewItem;
        }
    }

