package co.nz.demo.showposts.service.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.nz.demo.showposts.service.model.Post;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post_table")
    List<Post> getAllPosts();

    @Query("SELECT * FROM post_table WHERE  userId = :userId")
    Post getPostByUserId(long userId);

    @Query("SELECT * FROM post_table WHERE  id = :postId")
    Post getPostById(long postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Post> posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Post post);
}
