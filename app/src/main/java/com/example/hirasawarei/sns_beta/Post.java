package com.example.hirasawarei.sns_beta;

/**
 * Created by hirasawarei on 18/08/17.
 */

public class Post {

    int id;
    String userName, caption, postedTime, imageURL;
    int theNumberOfGood;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getTheNumberOfGood() {
        return theNumberOfGood;
    }

    public void setTheNumberOfGood(int theNumberOfGood) {
        this.theNumberOfGood = theNumberOfGood;
    }
}
