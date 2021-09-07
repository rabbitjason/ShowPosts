package co.nz.demo.showposts.service.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.nz.demo.showposts.service.model.Comment;

@Dao
public interface CommentDao {

    @Query("SELECT * FROM comment_table")
    List<Comment> getAllComments();

    @Query("SELECT * FROM comment_table WHERE  postId = :postId")
    List<Comment> getCommentsByPostId(long postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Comment> comments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comment comment);
}
