package co.nz.demo.showposts.view.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import co.nz.demo.showposts.R;

public class PostDetailActivity extends AppCompatActivity {

    public static final String POST_ID_ARG = "PostId";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null) {
            PostFragment postFragment = PostFragment.forPost(getIntent().getStringExtra(POST_ID_ARG));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, postFragment, PostFragment.TAG).commit();
        }
    }
}
