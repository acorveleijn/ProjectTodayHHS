package nl.hhs.apep2122group1.models;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.SET_NULL;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDateTime;

import nl.hhs.apep2122group1.utils.Converter;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "username",
                childColumns = "user_username",
                onDelete = CASCADE),

        @ForeignKey(
                entity = Label.class,
                parentColumns = "id",
                childColumns = "label_id",
                onDelete = SET_NULL)
})

public class Task implements Comparable<Task> {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String title;
    @TypeConverters({Converter.class})
    private LocalDateTime deadline;
    @TypeConverters({Converter.class})
    private LocalDateTime completed;
    private String description;

    @ColumnInfo(name = "user_username", collate = ColumnInfo.NOCASE)
    private String userUsername;

    @ColumnInfo(name = "label_id")
    private Integer labelId;

    public Task(String title, LocalDateTime deadline, String description, String userUsername, Integer labelId) {
        this.title = title;
        this.deadline = deadline;
        this.description = description;
        this.userUsername = userUsername;
        this.labelId = labelId;
    }

    public Task() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCompleted() {
        return completed;
    }

    public void setCompleted(LocalDateTime completed) {
        this.completed = completed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelOwnerId) {
        this.labelId = labelOwnerId;
    }

    @Override
    public int compareTo(Task task) {
        if (this.completed == null) { // task not yet completed
            if (this.getDeadline() == null || task.getDeadline() == null) {
                return 0;
            } else {
                return this.deadline.compareTo(task.getDeadline());
            }
        } else {
            return this.completed.compareTo(task.getCompleted());
        }
    }

    public void markTaskDone() {
        this.completed = LocalDateTime.now();
    }

    public void markTaskToDo() {
        this.completed = null;
    }
}
