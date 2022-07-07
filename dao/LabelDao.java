package nl.hhs.apep2122group1.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import nl.hhs.apep2122group1.models.Label;

@Dao
public interface LabelDao {
    @Query("SELECT * FROM label WHERE user_username = :username")
    List<Label> getAllFromUser(String username);

    @Query("SELECT * FROM label WHERE id = :id")
    Label getById(int id);

    @Insert
    void insert(Label task);

    @Update
    void update(Label task);

    @Delete
    void delete(Label task);
}
