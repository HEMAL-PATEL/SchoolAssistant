package com.denzcoskun.schoolassistant.weeklyschedule.activities;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.denzcoskun.schoolassistant.MainActivity;
import com.denzcoskun.schoolassistant.R;
import com.denzcoskun.schoolassistant.weeklyschedule.models.LessonModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditLessonActivity extends AppCompatActivity {

    @BindView(R.id.spinner_lessons)
    Spinner spinnerLessons;

    @BindView(R.id.image_button_edit_lesson)
    ImageButton imageButtonEditLesson;

    @BindView(R.id.edittext_classroom)
    EditText edittextClassroom;

    @BindView(R.id.text_view_start_time)
    TextView textViewStartTime;

    @BindView(R.id.image_button_edit_start_time)
    ImageButton imageButtonEditStartTime;

    @BindView(R.id.text_view_finish_time)
    TextView textViewFinishTime;

    @BindView(R.id.image_button_edit_finish_time)
    ImageButton imageButtonEditFinishTime;

    @BindView(R.id.button_add_lesson)
    Button buttonAddLesson;

    private String startHour;
    private String startMinute;
    private String finishHour;
    private String finishMinute;
    private boolean isStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_lesson);
        ButterKnife.bind(this);

        List<String> namesOfLessons = new ArrayList<>();
        namesOfLessons.add(getString(R.string.science));
        namesOfLessons.add(getString(R.string.mathematics));
        namesOfLessons.add(getString(R.string.history));

        int position = getIntent().getIntExtra("position", 0);
        int listItemPosition = getIntent().getIntExtra("listItemPosition", 0);

        initViews(position, listItemPosition);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            if (isStart) {
                startHour = Integer.toString(hourOfDay);
                startMinute = Integer.toString(minute);
                textViewStartTime.setText((startHour + ":" + startMinute));
            } else {
                finishHour = Integer.toString(hourOfDay);
                finishMinute = Integer.toString(minute);
                textViewFinishTime.setText((finishHour + ":" + finishMinute));
            }
        }, 0, 0, false);

        imageButtonEditStartTime.setOnClickListener(v -> {
            timePickerDialog.show();
            isStart = true;
        });

        imageButtonEditFinishTime.setOnClickListener(v -> {
            timePickerDialog.show();
            isStart = false;
        });

        buttonAddLesson.setOnClickListener(v -> {
            MainActivity.dayModels.get(position).getLessons()
                    .set(listItemPosition, new LessonModel(spinnerLessons.getSelectedItem().toString(),
                            spinnerLessons.getSelectedItemPosition(),
                            edittextClassroom.getText().toString(),
                            startHour + ":" + startMinute,
                            finishHour + ":" + finishMinute));
            MainActivity.lessonAdapters[position].notifyDataSetChanged();
            finish();
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, namesOfLessons);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLessons.setAdapter(dataAdapter);
    }

    public void initViews(int position, int listItemPosition) {
        LessonModel lessonModel = MainActivity.dayModels.get(position).getLessons().get(listItemPosition);

        startHour = lessonModel.getStartTime().split(":")[0];
        startMinute = lessonModel.getStartTime().split(":")[1];
        finishHour = lessonModel.getFinishTime().split(":")[0];
        finishMinute = lessonModel.getFinishTime().split(":")[1];

        textViewStartTime.setText((lessonModel.getStartTime().split(":")[0] + ":"
                + lessonModel.getStartTime().split(":")[1]));
        textViewFinishTime.setText((lessonModel.getFinishTime().split(":")[0] + ":"
                + lessonModel.getFinishTime().split(":")[1]));
        edittextClassroom.setText(lessonModel.getClassroom());
        spinnerLessons.setSelection(lessonModel.getPosition());
    }
}