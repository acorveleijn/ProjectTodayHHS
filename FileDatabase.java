package nl.hhs.apep2122group1.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import nl.hhs.apep2122group1.database.dao.LabelDao;
import nl.hhs.apep2122group1.database.dao.TaskDao;
import nl.hhs.apep2122group1.database.dao.UserDao;
import nl.hhs.apep2122group1.models.Label;
import nl.hhs.apep2122group1.models.Task;
import nl.hhs.apep2122group1.models.User;

@androidx.room.Database(entities = {
        Label.class,
        Task.class,
        User.class},
        version = 1,
        exportSchema = false)
public abstract class FileDatabase extends RoomDatabase implements Database {
    public abstract LabelDao labelDao();

    public abstract TaskDao taskDao();

    public abstract UserDao userDao();

    private static FileDatabase INSTANCE;

    public static FileDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (FileDatabase.class) {
                INSTANCE = Room.
                        databaseBuilder(
                                context,
                                FileDatabase.
                                        class,
                                "database")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return INSTANCE;
    }

    @Override
    public Task[] getAllTasks(String ownerUsername) {
        return taskDao().getAllFromUser(ownerUsername).toArray(new Task[0]);
    }

    @Override
    public Task getTask(int id) {
        return taskDao().getById(id);
    }

    @Override
    public void upsertTask(Task task) {
        if (task.getId() == null) {
            taskDao().insert(task);
        } else {
            taskDao().update(task);
        }
    }

    @Override
    public void deleteTask(Task task) {
        taskDao().delete(task);
    }

    @Override
    public Label getLabel(int id) {
        return labelDao().getById(id);
    }

    @Override
    public Label[] getAllLabels(String ownerUsername) {
        return labelDao().getAllFromUser(ownerUsername).toArray(new Label[0]);
    }

    @Override
    public void upsertLabel(Label label) {
        if (label.getId() == null) {
            labelDao().insert(label);
        } else {
            labelDao().update(label);
        }
    }

    @Override
    public void deleteLabel(Label label) {
        labelDao().delete(label);
    }

    @Override
    public User getUser(String username, String password) {
        return userDao().getByUsernameAndPassword(username, password);
    }

    @Override
    public User getUser(String username) {
        return userDao().getByUsername(username);
    }

    @Override
    public boolean insertUser(User user) {
        if (userDao().getByUsername(user.getUsername()) == null) {
            // user with the same username does not exist yet
            userDao().insert(user);
            return true;
        }
        return false;
    }
}