package com.example.mystaem;

import android.app.Activity;
import android.app.ProgressDialog;

public class Global {

    private static User user;
    private static String uid;
    private static ProgressDialog loadingDialog;
    private static Games game;
    private static Cart cart;



    public static void loadingDialogShow(Activity activity) {
        loadingDialog = ProgressDialog.show(activity, "", "Loading. Please wait...", true);
    }

    public static void loadingDialogDismiss() {
        if(loadingDialog != null) {
            loadingDialog.dismiss();
        }
        loadingDialog = null;
    }
    public static Games getGame() {
        return game;
    }

    public static void setGame(Games game) {
        Global.game = game;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        Global.uid = uid;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Global.user = user;
    }
    public static Cart getCart() {
        return cart;
    }

    public static void setCart(Cart cart) {
        Global.cart = cart;
    }

    public static void logoutUser() {
        user = null;
        uid = null;
    }
}
