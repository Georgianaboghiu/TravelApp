package com.example.planificareexcursii.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.planificareexcursii.utils.Trip;

import java.util.List;
@Dao
public interface TripDAO {
    @Query("SELECT * FROM trips")
    List<Trip> getAll();

    @Insert
    long insert(Trip trip);

    @Update
    int update(Trip trip);

    @Delete
    int delete(Trip trip);

    @Query("SELECT * FROM trips WHERE id=:userId")
    List<Trip> findTripsForUser(final int userId);
}
