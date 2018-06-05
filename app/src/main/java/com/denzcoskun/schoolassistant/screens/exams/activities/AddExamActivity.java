package com.denzcoskun.schoolassistant.screens.exams.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.denzcoskun.libdenx.activities.BaseActivity;
import com.denzcoskun.schoolassistant.R;
import com.denzcoskun.schoolassistant.activities.HomeActivity;
import com.denzcoskun.schoolassistant.helpers.DataHelper;
import com.denzcoskun.schoolassistant.screens.exams.models.ExamModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;

/**
 * Created by Denx on 30.05.2018.
 */
public class AddExamActivity extends BaseActivity {

    @BindView(R.id.textview_add_exam_name)
    EditText textviewAddExamName;

    @BindView(R.id.textview_add_exam_subject)
    EditText textviewAddExamSubject;

    @BindView(R.id.textview_add_exam_date)
    TextView textviewAddExamDate;

    @BindView(R.id.textview_add_exam_time)
    TextView textviewAddExamTime;

    @BindView(R.id.imagebutton_choose_date)
    ImageButton imagebuttonChooseDate;

    @BindView(R.id.imagebutton_choose_time)
    ImageButton imagebuttonChooseTime;

    @BindView(R.id.button_add_exam)
    Button buttonAddExam;

    private int year;
    private int month;
    private int day;
    private String hour;
    private String minute;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        addBackButton();
        setTitle(R.string.add_exam);
        DataHelper dataHelper = new DataHelper(AddExamActivity.this);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, dateOfYear, monthOfYear, dayOfMonth) -> {
            this.year = dateOfYear;
            this.month = monthOfYear;
            this.day = dayOfMonth;
            Date date = new GregorianCalendar(year, month, day).getTime();
            textviewAddExamDate.setText(dateOfString(date));
        }, year, month, day);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minuteOfHour) -> {
            if (hourOfDay < 10) {
                hour = "0" + Integer.toString(hourOfDay);
            } else {
                hour = Integer.toString(hourOfDay);
            }
            if (minuteOfHour < 10) {
                minute = "0" + Integer.toString(minuteOfHour);
            } else {
                minute = Integer.toString(minuteOfHour);
            }
            textviewAddExamTime.setText((hour + ":" + minute));

        }, 0, 0, false);

        imagebuttonChooseDate.setOnClickListener(v -> datePickerDialog.show());
        imagebuttonChooseTime.setOnClickListener(v -> timePickerDialog.show());

        buttonAddExam.setOnClickListener(v -> {
            Date date = new Date(year - 1900, month, day);
            HomeActivity.mainModel.examModels.add(new ExamModel(
                    textviewAddExamName.getText().toString(),
                    textviewAddExamSubject.getText().toString(),
                    dateOfString(date),
                    textviewAddExamTime.getText().toString()));
            dataHelper.setModel(HomeActivity.mainModel);
            finish();
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_add_exam;
    }

    public static String dateOfString(Date date) {
        return new SimpleDateFormat("dd MMMM EEEE yyyy").format(date);
    }

}