package nl.hhs.apep2122group1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDateTime;

import nl.hhs.apep2122group1.database.FileDatabase;
import nl.hhs.apep2122group1.utils.Alerts;
import nl.hhs.apep2122group1.utils.Converter;
import nl.hhs.apep2122group1.R;
import nl.hhs.apep2122group1.models.Label;
import nl.hhs.apep2122group1.models.Task;
import nl.hhs.apep2122group1.utils.Validators;


public class AddEditTaskActivity extends AppCompatActivity {

    private Label[] labels;
    private Task task;
    private String username;
    private int taskId;

    TextInputEditText description;
    TextInputEditText deadline;
    TextInputEditText title;
    TextView editHeader;
    Spinner labelSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        setTaskFromIntent();
        getTitleAndTexts();
        setLabelSpinner();

        if (taskId != -1) {
            editHeader.setText(R.string.edit_edit_title_pt);
            title.setText(task.getTitle());
            deadline.setText(task.getDeadline() == null ? "" : Converter.timeStampToInputString(task.getDeadline()));
            description.setText(task.getDescription());
        }
    }

    private void getTitleAndTexts() {
        editHeader = findViewById(R.id.add_edit_title_pt);
        title = findViewById(R.id.add_edit_name_ti_text);
        deadline = findViewById(R.id.add_edit_deadline_dt);
        description = findViewById(R.id.add_edit_description_etn_et);
        labelSpinner = findViewById(R.id.add_edit_label_sp_text);
    }

    private void setUsernameFromIntent() {
        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");
    }

    private void setTaskFromIntent() {
        Intent intent = getIntent();
        taskId = intent.getIntExtra("TASK_ID", -1);

        if (taskId != -1) {
            task = FileDatabase.getDatabase(this).getTask(taskId);
            username = task.getUserUsername();
        } else {
            setUsernameFromIntent();
        }
    }

    private void setLabelSpinner() {
        Label noLabel = new Label("<" + getApplicationContext().getResources().
                getString(R.string.no_label_text) + ">", "", "");
        Label[] tempList = FileDatabase.getDatabase(this).getAllLabels(username);
        labels = new Label[tempList.length + 1];
        System.arraycopy(tempList, 0, labels, 0, labels.length - 1);
        labels[labels.length - 1] = noLabel;

        Spinner choice = findViewById(R.id.add_edit_label_sp_text);
        ArrayAdapter<Label> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        choice.setAdapter(dataAdapter);
        if (taskId != -1) {

            if (task.getLabelId() != null) {
                for (int i = 0; i < labels.length; i++) {
                    if (task.getLabelId().equals(labels[i].getId())) {
                        choice.setSelection(i);
                    }
                }
            } else {
                choice.setSelection(labels.length - 1);
            }
        } else {
            choice.setSelection(labels.length - 1);
        }
    }

    public void upsertTask(View view) {
        if (taskId == -1) {
            makeTask();
        } else {
            updateTask();
        }
    }

    private void updateTask() {
        String titleString = title.getText().toString().trim();
        String descriptionString = description.getText().toString().trim();
        String deadlineString = deadline.getText().toString();
        Label selectedLabel = (Label) labelSpinner.getSelectedItem();

        LocalDateTime deadlineInput = Converter.inputStringToTimeStamp(deadlineString);

        if (Validators.validateDateIsEmptyOrNotNull(deadlineString) && Validators.validateStringNotNullOrEmpty(titleString)) {
            task.setTitle(titleString);
            task.setDescription(descriptionString);
            task.setLabelId(selectedLabel.getId());
            task.setDeadline(deadlineInput);

            FileDatabase.getDatabase(this).upsertTask(task);
            Toast.makeText(this, R.string.add_edit_save_btn_id, Toast.LENGTH_SHORT)
                    .show();

            Intent intent = new Intent(this, TaskListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        } else {
            Toast.makeText(this, (R.string.toast_error_text), Toast.LENGTH_SHORT).show();
        }
    }

    private void makeTask() {
        String titleString = title.getText().toString().trim();
        String descriptionString = description.getText().toString().trim();
        String deadlineString = deadline.getText().toString();

        LocalDateTime deadlineInput = Converter.inputStringToTimeStamp(deadlineString);
        Label selectedLabel = (Label) labelSpinner.getSelectedItem();

        if (Validators.validateDateIsEmptyOrNotNull(deadlineString) && Validators.validateStringNotNullOrEmpty(titleString)) {
            Task task = new Task(titleString, deadlineInput, descriptionString, username, selectedLabel.getId());

            FileDatabase.getDatabase(this).upsertTask(task);
            Toast.makeText(this, R.string.add_edit_save_btn_id, Toast.LENGTH_SHORT)
                    .show();
            finish();
        } else {
            Toast.makeText(this, (R.string.toast_error_text), Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackBtnPressed(View view) {
        finish();

        Toast.makeText(this, R.string.back_btn_text, Toast.LENGTH_SHORT)
                .show();
    }

    public void onClear(View view) {
        title.getText().clear();
        deadline.getText().clear();
        labelSpinner.setSelection(labels.length - 1);
        description.getText().clear();

        Toast.makeText(this, R.string.add_edit_clear_btn_id, Toast.LENGTH_SHORT)
                .show();
    }

    public void onCalendarBtnPressed(View view) {
        LocalDateTime dateTime = task != null ? task.getDeadline() : LocalDateTime.now();
        Alerts.openDateEditDialog(this, dateTime, (onDateTimeChanged) -> {
            deadline.setText(Converter.timeStampToInputString(onDateTimeChanged));
        });
    }
}