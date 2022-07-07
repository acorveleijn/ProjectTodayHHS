package nl.hhs.apep2122group1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import nl.hhs.apep2122group1.database.FileDatabase;
import nl.hhs.apep2122group1.utils.Converter;
import nl.hhs.apep2122group1.R;
import nl.hhs.apep2122group1.models.Label;
import nl.hhs.apep2122group1.models.Task;

public class ViewTaskActivity extends AppCompatActivity {
    private int taskId;
    private Label label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        taskId = intent.getIntExtra("TASK_ID", -1);
        Task task = FileDatabase.getDatabase(this).getTask(taskId);

        if (task.getLabelId() != null) {
            label = FileDatabase.getDatabase(this).getLabel(task.getLabelId());
        }

        TextView title = findViewById(R.id.view_name_task_tv);
        TextView deadline = findViewById(R.id.view_deadline_tv);
        TextView completed = findViewById(R.id.view_completed_tv);
        TextView labelName = findViewById(R.id.view_label_tv);
        TextView description = findViewById(R.id.view_description_tv);

        title.setText(task.getTitle());
        deadline.setText(
                task.getDeadline() == null ? getResources().getString(R.string.no_deadline_text) : Converter.timeStampToReadableString(task.getDeadline()));
        completed.setText(
                task.getCompleted() == null ? getResources().getString(R.string.view_in_progress_text) : Converter.timeStampToReadableString(task.getCompleted()));
        labelName.setText(
                task.getLabelId() == null ? getResources().getString(R.string.no_label_text) : String.valueOf(label.getTitle()));
        description.setText(
                task.getDescription().trim().equals("") ? getResources().getString(R.string.no_description_text) : String.valueOf(task.getDescription()));
    }

    public void onEditBtnPressed(View view) {
        Intent intent = new Intent(this, AddEditTaskActivity.class);
        intent.putExtra("TASK_ID", taskId);
        this.startActivity(intent);
    }

    public void onBackBtnPressed(View view) {
        finish();
    }
}