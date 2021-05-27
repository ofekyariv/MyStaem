package com.example.mystaem;

public class Games {
    String gamecode;
    String kind;
    String name;
    String price;
    String pic;
    int likes;
    int dislikes;
    boolean rated;
    boolean avilable;

    public Games(String gamecode, String kind, String name, String price,String pic, boolean avilable) {
        this.gamecode = gamecode;
        this.kind = kind;
        this.name = name;
        this.price = price;
        this.pic = pic;
        this.avilable = avilable;
        this.likes =0;
        this.dislikes=0;
        this.rated= false;

    }


    public Games(String gamecode, String kind, String name, String price, boolean avilable) {
        this.gamecode = gamecode;
        this.kind = kind;
        this.name = name;
        this.price = price;
        this.avilable = avilable;
        this.pic = "abc";
        this.likes =0;
        this.dislikes=0;
        this.rated= false;

    }
    public Games(){

    }
    public String getGamecode() {
        return gamecode;
    }

    public void setGamecode(String gamecode) {
        this.gamecode = gamecode;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Boolean getAvilable() {
        return avilable;
    }

    public void setAvilable(Boolean avilable) {
        this.avilable = avilable;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public Boolean getRated() {
        return rated;
    }

    public void setRated(Boolean rated) {
        this.rated = rated;
    }

}
