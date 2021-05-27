package com.example.mystaem;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;



public class UserList extends ArrayAdapter<User> {

    private Activity context;
    private List<User> userList;


    public UserList(Activity context, List<User> userList){
        super(context, R.layout.cardview, userList);
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        final View listViewItem = inflater.inflate(R.layout.cardview, null, true);


        final TextView txtname = (TextView) listViewItem.findViewById(R.id.txtname);
        TextView txtemail = (TextView) listViewItem.findViewById(R.id.txtemail);
        TextView txtpass = (TextView) listViewItem.findViewById(R.id.txtpass);

        final User user = userList.get(position);

        txtname.setText(user.getUsername());
        txtemail.setText(user.getEmail());
        txtpass.setText(user.getPass());


        return listViewItem;
    }


}
