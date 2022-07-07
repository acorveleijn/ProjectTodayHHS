package nl.hhs.apep2122group1.models;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "username",
                childColumns = "user_username",
                onDelete = CASCADE),
})

public class Label {
    @PrimaryKey(autoGenerate = true)
    private Integer id;
    private String title;
    private String colorCode;

    @ColumnInfo(name = "user_username", collate = ColumnInfo.NOCASE)
    private String userUsername;

    public Label(String title, String colorCode, String userUsername) {
        this.title = title;
        this.colorCode = colorCode;
        this.userUsername = userUsername;
    }

    public Label() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) { // not needed due to autoGenerate?
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    @Override
    public String toString() {
        return title;
    }
}
