package co.nz.demo.showposts.service.db.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import co.nz.demo.showposts.service.db.dao.CommentDao;
import co.nz.demo.showposts.service.db.dao.PostDao;
import co.nz.demo.showposts.service.model.Comment;
import co.nz.demo.showposts.service.model.Post;

@Database(entities = {Post.class, Comment.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    public abstract PostDao postDao();
    public abstract CommentDao commentDao();

    public static AppDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,
                            "post_comment.db").
                            addMigrations(AppDatabase.MIGRATION_1_2).// Update the database when modifying fields of the database
                            allowMainThreadQueries().// Allow to read the database in the main thread
                            build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * When the Database changed, adding Migration
     */
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Todo
            //  If there are fields to be modified in the database,
            //  using database.execSQL()  and modifying the version of the database at the same time.
        }
    };
}
