package nl.hhs.apep2122group1.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import nl.hhs.apep2122group1.models.Task;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM task WHERE user_username = :username")
    List<Task> getAllFromUser(String username);

    @Query("SELECT * FROM task WHERE id = :id")
    Task getById(int id);

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);
}
