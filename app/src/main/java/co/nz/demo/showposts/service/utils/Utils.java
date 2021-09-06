package co.nz.demo.showposts.service.utils;

import android.util.Log;

import java.util.List;

import co.nz.demo.showposts.service.model.Post;

public class Utils {
    public static void printMsg(String msg) {
        Log.i(Constants.TAG, msg);
    }

    public static void printPostInfo(List<Post> posts) {
        if (posts == null || posts.size() == 0) {
            Log.i(Constants.TAG, " post list is empty");
            return;
        }

        for (int i = 0; i<posts.size(); i++) {
            Post post = posts.get(i);
            Log.i(Constants.TAG, "No: " + i + " post title: =" + post.title + " \n");
        }
    }

    public static void printPostInfo(Post post) {
        if (post == null) {
            Log.i(Constants.TAG, " post  is null");
            return;
        }
        Log.i(Constants.TAG, " post title =" + post.title);
    }
}
