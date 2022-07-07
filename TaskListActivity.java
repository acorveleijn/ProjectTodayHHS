package nl.hhs.apep2122group1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.hhs.apep2122group1.R;
import nl.hhs.apep2122group1.utils.Alerts;
import nl.hhs.apep2122group1.database.FileDatabase;
import nl.hhs.apep2122group1.utils.Sorting;
import nl.hhs.apep2122group1.adapters.TaskAdapter;
import nl.hhs.apep2122group1.models.Label;
import nl.hhs.apep2122group1.models.Task;
import nl.hhs.apep2122group1.models.User;

public class TaskListActivity extends AppCompatActivity {
    private TaskAdapter adapter;
    private List<Task> tasks;
    private ArrayList<Task> sortedTasks;
    private User user;
    private Sorting sorting = Sorting.DEFAULT;
    private int selectedFilterId = -1;
    private boolean toDo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUser();
        setTaskList();
    }

    private void getUser() {
        Intent intent = getIntent();
        String username = intent.getStringExtra("USERNAME");
        user = FileDatabase.getDatabase(this).getUser(username);
        TextView title = findViewById(R.id.task_list_title_tv_id);
        title.setText(String.format(getResources().getString(R.string.task_list_title_tv_text), user.getName()));
    }

    private void setTaskList() {
        selectedFilterId = -1;
        setTasks();
        filterAndSortTasksByStatus();
        setList();
    }

    private void setTasks() {
        tasks = Arrays.asList(FileDatabase.getDatabase(this).getAllTasks(user.getUsername()));
        sortedTasks = new ArrayList<>();
    }

    private void filterAndSortTasksByStatus() {
        filterTasksByStatus();
        sortTasksByStatus();
    }

    private void filterTasksByStatus() {
        sortedTasks.clear();
        for (Task task : tasks) {
            if (toDo && task.getCompleted() == null) {
                sortedTasks.add(task);
            }
            if (!toDo && task.getCompleted() != null) {
                sortedTasks.add(task);
            }
        }
    }

    private void sortTasksByStatus() {
        if (toDo) {
            if (sorting == Sorting.ASCENDING || sorting == Sorting.DEFAULT) {
                Collections.sort(sortedTasks);
            } else {
                sortedTasks.sort(Collections.reverseOrder());
            }
        } else {
            if (sorting == Sorting.DESCENDING || sorting == Sorting.DEFAULT) {
                sortedTasks.sort(Collections.reverseOrder());
            } else {
                Collections.sort(sortedTasks);
            }
        }
    }

    private void setList() {
        RecyclerView recyclerView = findViewById(R.id.task_list_rv_id);
        adapter = new TaskAdapter(this, sortedTasks, () -> setTaskList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void onLabelBtnPressed(View view) {
        Intent intent = new Intent(this, LabelsActivity.class);
        intent.putExtra("USERNAME", user.getUsername());
        this.startActivity(intent);
    }

    public void onSortBtnPressed(View view) {
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);

        // inflate & set checked based on state:
        popupMenu.getMenuInflater().inflate(R.menu.popup_sort, popupMenu.getMenu());

        if (sorting == Sorting.DEFAULT) {
            popupMenu.getMenu().findItem(R.id.popup_sort_default_id).setChecked(true);
        }
        if (sorting == Sorting.ASCENDING) {
            popupMenu.getMenu().findItem(R.id.popup_sort_ascending_id).setChecked(true);
        }
        if (sorting == Sorting.DESCENDING) {
            popupMenu.getMenu().findItem(R.id.popup_sort_descending_id).setChecked(true);
        }

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.popup_sort_default_id) {
                if (toDo) {
                    Collections.sort(sortedTasks);
                } else {
                    sortedTasks.sort(Collections.reverseOrder());
                }
                sorting = Sorting.DEFAULT;
                setList();
                return true;
            }

            if (menuItem.getItemId() == R.id.popup_sort_ascending_id) {
                Collections.sort(sortedTasks);
                sorting = Sorting.ASCENDING;
                setList();
                return true;
            }
            if (menuItem.getItemId() == R.id.popup_sort_descending_id) {
                sortedTasks.sort(Collections.reverseOrder());
                sorting = Sorting.DESCENDING;
                setList();
                return true;
            }
            return false;
        });
        popupMenu.show();
    }

    public void onFilterBtnPressed(View view) {
        ArrayList<Task> filteredTasks = new ArrayList<>();
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), view);

        Label[] labels = FileDatabase.getDatabase(this).getAllLabels(user.getUsername());
        ArrayList<Label> uniqueLabels = new ArrayList<>();

        popupMenu.getMenuInflater().inflate(R.menu.popup_filter, popupMenu.getMenu());
        if (selectedFilterId == -1) {
            popupMenu.getMenu().findItem(R.id.popup_filter_all_id).setChecked(true);
        }
        if (selectedFilterId == -2) {
            popupMenu.getMenu().findItem(R.id.popup_filter_noLabel_id).setChecked(true);
        }

        for (int i = 0; i < labels.length; i++) {
            uniqueLabels.add(labels[i]);
            popupMenu.getMenu().add(0, labels[i].getId(), i + 2, labels[i].getTitle());
            popupMenu.getMenu().findItem(labels[i].getId()).setCheckable(true);
            if (selectedFilterId == labels[i].getId()) {
                MenuItem item = popupMenu.getMenu().findItem(labels[i].getId());
                item.setChecked(true);
            }
        }

        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.popup_filter_all_id) {
                sortedTasks = new ArrayList<>(tasks);
                filterAndSortTasksByStatus();
                selectedFilterId = -1;
                setList();
                return true;
            }
            if (menuItem.getItemId() == R.id.popup_filter_noLabel_id) {
                sortedTasks = new ArrayList<>(tasks);
                filterAndSortTasksByStatus();

                for (Task task : sortedTasks) {
                    if (task.getLabelId() == null) {
                        filteredTasks.add(task);
                    }
                }
                sortedTasks = new ArrayList<>(filteredTasks);
                selectedFilterId = -2;
                setList();
                return true;
            }

            for (int i = 0; i < uniqueLabels.size(); i++) {
                if (uniqueLabels.get(i).getId() == menuItem.getItemId()) {
                    sortedTasks = new ArrayList<>(tasks);
                    filterAndSortTasksByStatus();

                    for (Task task : sortedTasks) {
                        if (task.getLabelId() != null && task.getLabelId().equals(uniqueLabels.get(i).getId())) {
                            filteredTasks.add(task);
                        }
                    }
                    selectedFilterId = uniqueLabels.get(i).getId();
                    sortedTasks = new ArrayList<>(filteredTasks);
                    setList();
                    return true;
                }
            }
            return false;
        });
        popupMenu.show();
    }

    public void onToDoBtnPressed(View view) {
        selectedFilterId = -1;
        toDo = true;
        filterAndSortTasksByStatus();
        adapter.notifyDataSetChanged();
    }

    public void onDoneBtnPressed(View view) {
        selectedFilterId = -1;
        toDo = false;
        filterAndSortTasksByStatus();
        adapter.notifyDataSetChanged();
    }

    public void onCheckChanged(View view) { // bound to checkbox for updating view
        MaterialButton toDoBtn = findViewById(R.id.task_list_todo_mb_id);
        filterAndSortTasksByStatus();
        adapter.notifyDataSetChanged();
    }

    public void onAddBtnPressed(View view) {
        Intent intent = new Intent(this, AddEditTaskActivity.class);
        intent.putExtra("USERNAME", user.getUsername());
        this.startActivity(intent);
    }

    public void onLogoutBtnPressed(View v) {
        Alerts.logoutAlert(this);
    }

    @Override
    public void onBackPressed() {
        Alerts.logoutAlert(this);
    }
}