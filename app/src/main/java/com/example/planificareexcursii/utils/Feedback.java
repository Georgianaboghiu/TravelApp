package com.example.planificareexcursii.utils;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "feedback")
public class Feedback {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    private long id;
    @ColumnInfo(name="userId")
    private long userId;
    @ColumnInfo(name="valueFeedback")
    private int valueFeedback;
    @ColumnInfo(name="textFeedback")
    private String textFeedback;

    public long getUserId() {
        return userId;
    }

    public int getValueFeedback() {
        return valueFeedback;
    }

    public String getTextFeedback() {
        return textFeedback;
    }

    @Ignore
    public Feedback(){

    }

    @Ignore
    public Feedback(long userId, int valueFeedback, String textFeedback) {
        this.userId = userId;
        this.valueFeedback = valueFeedback;
        this.textFeedback = textFeedback;
    }


    public Feedback(long id, long userId, int valueFeedback, String textFeedback) {
        this.id=id;
        this.userId = userId;
        this.valueFeedback = valueFeedback;
        this.textFeedback = textFeedback;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "userId='" + userId + '\'' +
                ", valueFeedback=" + valueFeedback +
                ", textFeedback='" + textFeedback + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setValueFeedback(int valueFeedback) {
        this.valueFeedback = valueFeedback;
    }

    public void setTextFeedback(String textFeedback) {
        this.textFeedback = textFeedback;
    }
}

