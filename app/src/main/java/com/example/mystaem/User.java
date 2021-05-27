package com.example.mystaem;

public class User {
    String email;
    String username;
    String pass;
    String phone;
    String lastname;
    String firstname;
    String pic;
    String region;
    String birth;
    String gender;
    String type ;
    public User(String email, String username, String pass, String phone, String lastname, String firstname,String birth ) {
        this.email = email;
        this.username = username;
        this.pass = pass;
        this.phone = phone;
        this.lastname = lastname;
        this.firstname = firstname;
        this.type ="user";
        this.pic = pic;
        this.birth = birth;
        this.gender = gender;
    }
    public User(){

    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }

    public String getLastname() {
        return lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPic() {
        return pic;
    }

    public String getBirth() {
        return birth;
    }

    public String getGender() {
        return gender;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }



    public void setBirth(String birth) {
        this.birth = birth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
