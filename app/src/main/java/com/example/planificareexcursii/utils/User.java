package com.example.planificareexcursii.utils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "users")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="userId")
    private long userId;
    @ColumnInfo(name="firstname")
    private String firstname;
    @ColumnInfo(name="lastname")
    private String lastname;
    @ColumnInfo(name="password")
    private String password;
    @ColumnInfo(name="email")
    private String email;
    @ColumnInfo(name="age")
    private int age;

    @Ignore
    public User(){

    }

    @Ignore
    public User(String firstname, String lastname, String password, String email, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public User(long userId, String firstname, String lastname, String password, String email, int age) {
        this.userId = userId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +"id "+userId+
                '}';
    }
}
