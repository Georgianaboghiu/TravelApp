package com.example.planificareexcursii.database;

import android.content.Context;
import android.util.Log;

import com.example.planificareexcursii.asyncTask.AsyncTaskRunner;
import com.example.planificareexcursii.asyncTask.Callback;
import com.example.planificareexcursii.utils.Trip;

import java.util.List;
import java.util.concurrent.Callable;

public class TripService {
    private final TripDAO tripDAO;
    private final AsyncTaskRunner asyncTaskRunner;

    public TripService(Context context) {
        tripDAO= DatabaseManager.getInstance(context).getTripDAO();
        asyncTaskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<Trip>> callback) {
        Callable<List<Trip>> callable = new Callable<List<Trip>>() {
            @Override
            public List<Trip> call() {
                return tripDAO.getAll();
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
    public void insert(Callback<Trip> callback, final Trip trip) {
        Callable<Trip> callable = new Callable<Trip>() {
            @Override
            public Trip call() {
                if (trip == null) {
                    return null;
                }
                long id = tripDAO.insert(trip);
                if (id == -1) {
                    return null;
                }
                trip.setId(id);
                return trip;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
        Log.v("insert", "inserted"+trip.toString());
    }

    public void update(Callback<Trip> callback, final Trip trip) {
        Callable<Trip> callable = new Callable<Trip>() {
            @Override
            public Trip call() {
                if (trip == null) {
                    return null;
                }
                int count = tripDAO.update(trip);
                if (count < 1) {
                    return null;
                }
                return trip;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Trip trip) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (trip == null) {
                    return -1;
                }
                return tripDAO.delete(trip);
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
