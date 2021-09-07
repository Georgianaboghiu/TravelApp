package com.example.planificareexcursii.database;

import android.content.Context;
import android.util.Log;

import com.example.planificareexcursii.asyncTask.AsyncTaskRunner;
import com.example.planificareexcursii.asyncTask.Callback;
import com.example.planificareexcursii.utils.Feedback;

import java.util.List;
import java.util.concurrent.Callable;
public class FeedbackService {

    private final FeedbackDAO feedbackDAO;
    private final AsyncTaskRunner asyncTaskRunner;

    public FeedbackService(Context context) {
        feedbackDAO = DatabaseManager.getInstance(context).getFeedbackDAO();
        asyncTaskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<Feedback>> callback) {
        Callable<List<Feedback>> callable = new Callable<List<Feedback>>() {
            @Override
            public List<Feedback> call() {
                return feedbackDAO.getAll();
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Feedback> callback, final Feedback feedback) {
        Callable<Feedback> callable = new Callable<Feedback>() {
            @Override
            public Feedback call() {
                if (feedback == null) {
                    return null;
                }
                long id = feedbackDAO.insert(feedback);
                if (id == -1) {
                    return null;
                }
                feedback.setId(id);
                return feedback;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
        Log.v("insert", "inserted"+feedback.toString());
    }

    public void update(Callback<Feedback> callback, final Feedback feedback) {
        Callable<Feedback> callable = new Callable<Feedback>() {
            @Override
            public Feedback call() {
                if (feedback == null) {
                    return null;
                }
                int count = feedbackDAO.update(feedback);
                if (count < 1) {
                    return null;
                }
                return feedback;
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Feedback feedback) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (feedback == null) {
                    return -1;
                }
                return feedbackDAO.delete(feedback);
            }
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

}

