package co.nz.demo.showposts.service.network;

import java.util.List;

import co.nz.demo.showposts.service.model.Comment;
import co.nz.demo.showposts.service.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NetWorkApiService {
    public static String HTTPS_API_OPEN_URL = "https://jsonplaceholder.typicode.com/";

    //Get a list of Post
    @GET("posts")
    Call<List<Post>> getPostList();

    //Get a list of Comment of a Post
    @GET("comments")
    Call<List<Comment>> getCommentList(@Query("postId") String postId);
}
