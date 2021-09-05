package co.nz.demo.showposts.service.network;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.TimeUnit;

import co.nz.demo.showposts.service.db.database.AppDatabase;
import co.nz.demo.showposts.service.model.Comment;
import co.nz.demo.showposts.service.model.Post;
import co.nz.demo.showposts.service.utils.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private NetWorkApiService netWorkApiService;
    private static RetrofitManager mRetrofitManager;
    private Context mContext;// this manager needs to hold the context

    private RetrofitManager(Context context) {
        this.mContext = context;
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS) // set timeout
                .retryOnConnectionFailure(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkApiService.HTTPS_API_OPEN_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        netWorkApiService = retrofit.create(NetWorkApiService.class);
    }

    public static RetrofitManager getInstance(Context context) {
        if (mRetrofitManager == null) {
            synchronized(RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager(context);
                }
            }
        }
        return mRetrofitManager;
    }

    public MutableLiveData<List<Post>> getPostList() {
        final MutableLiveData<List<Post>> data = new MutableLiveData<>();

        netWorkApiService.getPostList().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                List<Post> posts = response.body();
                // After the network returns the packet, it is persisted to the local
                AppDatabase.getInstance(mContext).postDao().insertAll(posts);
                // Observer mode, callback to update the UI
                data.setValue(posts);

                Log.i(Constants.TAG, "onResponse()");
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                data.setValue(null);
                Log.i(Constants.TAG, "onFailure()");
            }
        });

        return data;
    }

    public MutableLiveData<List<Comment>> getCommentList(String postId) {

        final MutableLiveData<List<Comment>> data = new MutableLiveData<>();

        netWorkApiService.getCommentList(postId).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                List<Comment> comments = response.body();
                // After the network returns the packet, it is persisted to the local
                AppDatabase.getInstance(mContext).commentDao().insertAll(comments);
                // Observer mode, callback to update the UI
                data.setValue(comments);

                Log.i(Constants.TAG, "onResponse()");
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                data.setValue(null);
                Log.i(Constants.TAG, "onFailure()");
            }
        });

        return data;
    }
}
