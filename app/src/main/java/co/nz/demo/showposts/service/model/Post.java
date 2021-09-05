package co.nz.demo.showposts.service.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "post_table")
public class Post {
    @PrimaryKey
    public long id;
    @ColumnInfo
    public long userId;
    @ColumnInfo
    public String title;
    @ColumnInfo
    public String body;

    public Post() {

    }

    @Override
    public String toString() {
        return "Post:" +
                "title =" + title +
                ", body =" + body;
    }
}
