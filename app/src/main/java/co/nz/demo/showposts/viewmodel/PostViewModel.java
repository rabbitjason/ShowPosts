package co.nz.demo.showposts.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import co.nz.demo.showposts.service.db.database.AppDatabase;
import co.nz.demo.showposts.service.model.Post;
import co.nz.demo.showposts.service.network.RetrofitManager;
import co.nz.demo.showposts.service.utils.Utils;

public class PostViewModel extends AndroidViewModel {

    private MutableLiveData<Post> mPostObservableLocal = new MutableLiveData<>();
    private MutableLiveData<Post> mPostObservableNetwork = new MutableLiveData<>();
    private final String postID;

    public PostViewModel(@NonNull Application application, String postID) {
        super(application);
        this.postID = postID;
    }

    public Post loadDataInfo(String postId) {
        Utils.printMsg("Load the post detail info");
        Post post = AppDatabase.getInstance(this.getApplication()).postDao().getPostById(Integer.parseInt(postId));
        mPostObservableLocal.setValue(post);
        return post;
    }

    public void requestSearchPost(String postId) {
        mPostObservableNetwork = RetrofitManager.getInstance(this.getApplication()).searchPost(postId);
    }

    public LiveData<Post> getmPostObservableNetwork() {
        return mPostObservableNetwork;
    }

    public LiveData<Post> getmPostObservableLocal() {
        return mPostObservableLocal;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String postID;

        public Factory(@NonNull Application application, String postID) {
            this.application = application;
            this.postID = postID;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PostViewModel(application, postID);
        }
    }
}
