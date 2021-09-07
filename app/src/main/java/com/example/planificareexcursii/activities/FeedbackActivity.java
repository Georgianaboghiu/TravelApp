package com.example.planificareexcursii.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planificareexcursii.R;
import com.example.planificareexcursii.asyncTask.Callback;
import com.example.planificareexcursii.database.FeedbackService;
import com.example.planificareexcursii.utils.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {

    private EditText et_feedback;
    private Button btn_feedback;
    private SeekBar seekBar;
    private TextView tv_valueFeedback;
    private int valueFeedback;

    private List<Feedback> listFeedback = new ArrayList<>();
    private FeedbackService feedbackService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        initComponents();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_valueFeedback.setText(progress+"/100");
                valueFeedback=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackService.insert(insertIntoDbCallback(), sendFeedback());

            }
        });

    }

    private void initComponents(){
        seekBar=findViewById(R.id.seekBarFeedback);
        btn_feedback=findViewById(R.id.submit_feedback);
        et_feedback=findViewById(R.id.edit_text_feedback);
        tv_valueFeedback=findViewById(R.id.tv_valueFeedback);
        feedbackService = new FeedbackService(getApplicationContext());
        feedbackService.getAll(getAllFromDbCallback());
    }

    public Feedback sendFeedback() {
        Feedback feedback=new Feedback();
        feedback.setValueFeedback(valueFeedback);
        feedback.setTextFeedback(et_feedback.getText().toString());
        Toast.makeText(getApplicationContext(), "Thanks for your feedback!", Toast.LENGTH_SHORT).show();
        return  feedback;
    }

    private Callback<List<Feedback>> getAllFromDbCallback() {
        return new Callback<List<Feedback>>() {
            @Override
            public void runResultOnUiThread(List<Feedback> result) {
                if (result != null) {
                    listFeedback.clear();
                    listFeedback.addAll(result);
                }
            }
        };
    }


    private Callback<Feedback> insertIntoDbCallback() {
        return new Callback<Feedback>() {
            @Override
            public void runResultOnUiThread(Feedback result) {
                if (result != null) {
                    listFeedback.add(result);

                }
            }
        };
    }

    private Callback<Feedback> updateToDbCallback() {
        return new Callback<Feedback>() {
            @Override
            public void runResultOnUiThread(Feedback result) {
                if (result != null) {
                    for (Feedback feedback : listFeedback) {
                        if (feedback.getId() == result.getId()) {
                            feedback.setValueFeedback(result.getValueFeedback());
                            feedback.setTextFeedback(result.getTextFeedback());
                            break;
                        }
                    }
                }
            }
        };
    }

    private Callback<Integer> deleteToDbCallback(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    listFeedback.remove(position);
                }
            }
        };
    }
}