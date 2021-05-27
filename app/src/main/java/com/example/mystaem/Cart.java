package com.example.mystaem;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cart {


    private String email;
    private String userId;
    private ArrayList<Games> mygames;
    private Boolean bought;
    private String CartCode;




    public Cart(Boolean bought,String userId,String email) {
        this.bought = bought;
        this.userId =userId;
        this.email = email;
        this.mygames =new ArrayList<Games>();
        this.CartCode="";

    }
    public Cart()
    {

    }

    public void AddProduct(Games p){
            if(this.mygames==null)
                this.mygames = new ArrayList<Games>();
            this.mygames.add(p);

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Games> getProductList() {
        return mygames;
    }

    public void setProductList(ArrayList<Games> gamesList) {
        this.mygames = mygames;
    }

    public Boolean getBought() {
        return bought;
    }

    public void setBought(Boolean bought) {
        this.bought = bought;
    }

    public String getCartCode() {
        return CartCode;
    }

    public void setCartCode(String cartCode) {
        CartCode = cartCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
