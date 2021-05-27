package com.example.mystaem;


    public class MyData {

        private static User user;

        public static User getUser() {
            return user;
        }

        public static void setUser(User user) {
            MyData.user = user;
        }
    }

