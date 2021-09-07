package com.example.planificareexcursii.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.planificareexcursii.utils.User;

import java.util.List;
@Dao
public interface UserDAO {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE email=(:name) AND password=(:pass)")
    User login(String name, String pass);


    @Query("SELECT * FROM users WHERE firstname=(:name)")
    User retrieveInfo(String name);

    @Insert
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    int delete(User user);
}
