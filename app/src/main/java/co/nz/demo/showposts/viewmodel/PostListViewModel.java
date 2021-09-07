package co.nz.demo.showposts.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import co.nz.demo.showposts.service.db.database.AppDatabase;
import co.nz.demo.showposts.service.model.Post;
import co.nz.demo.showposts.service.network.RetrofitManager;
import co.nz.demo.showposts.service.utils.Utils;

public class PostListViewModel extends AndroidViewModel {

    /**
     * Here to distinguish between local and network
     */
    private MutableLiveData<List<Post>> postListObserableLocal = new MutableLiveData<>();
    private MutableLiveData<List<Post>> postListObserableNetwork = new MutableLiveData<>();

    public PostListViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * Get posts from local or cache
     */
    public  List<Post> loadDataInfo() {
        Utils.printMsg( " Load post list data");
        List<Post> posts = AppDatabase.getInstance(this.getApplication()).postDao().getAllPosts();
        postListObserableLocal.setValue(posts);
        Utils.printPostInfo(posts);
        return posts;
    }

    /**
     * Send network request to get posts
     */
    public void requestNetwork() {
        postListObserableNetwork = RetrofitManager.getInstance(this.getApplication()).getPostList();
    }


    /**
     * Return liveData : the observed object is List<Post>
     * @return
     */
    public LiveData<List<Post>> getPostListObserable() {
        return postListObserableNetwork;
    }

    public LiveData<List<Post>> getPostListObserableLocal() {
        return postListObserableLocal;
    }

}
