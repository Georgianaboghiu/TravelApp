package com.example.planificareexcursii.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.planificareexcursii.CustomAdapter.DateConverter;
import com.example.planificareexcursii.utils.Feedback;
import com.example.planificareexcursii.utils.Trip;
import com.example.planificareexcursii.utils.User;


@Database(entities = {Trip.class, User.class,Feedback.class}, exportSchema = false, version = 12)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context){
        synchronized (DatabaseManager.class){
            if(databaseManager == null){
                databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "Licenta").
                        fallbackToDestructiveMigration().build();
            }
        }
        return databaseManager;
    }
    public abstract FeedbackDAO getFeedbackDAO();
    public abstract TripDAO getTripDAO();
    public abstract UserDAO getUserDAO();
}
