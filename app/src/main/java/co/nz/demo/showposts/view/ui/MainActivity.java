package co.nz.demo.showposts.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import co.nz.demo.showposts.R;
import co.nz.demo.showposts.service.model.Post;
import co.nz.demo.showposts.service.utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            PostListFragment fragment = new PostListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, PostListFragment.TAG).commit();
        }
    }

    /**
     * Click on a specific item to open its detailed view
     * @param post
     */
    public void show(Post post) {
        // Todo
        Utils.printMsg("Click post item: " + post.title);
    }
}
