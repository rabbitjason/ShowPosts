package co.nz.demo.showposts.service.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comment_table")
public class Comment {
    @PrimaryKey
    public long id;
    @ColumnInfo
    public long postId;
    @ColumnInfo
    public String name;
    @ColumnInfo
    public String email;
    @ColumnInfo
    public String body;

    public Comment() {

    }

    @Override
    public String toString() {
        return "Comment:" +
                "name =" + name +
                "email =" + email +
                ", body =" + body;
    }
}
