package com.example.planificareexcursii.asyncTask;

public interface Callback<R> {
    void runResultOnUiThread(R result);
}
