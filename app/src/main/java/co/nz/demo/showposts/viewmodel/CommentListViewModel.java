package co.nz.demo.showposts.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import co.nz.demo.showposts.service.db.database.AppDatabase;
import co.nz.demo.showposts.service.model.Comment;
import co.nz.demo.showposts.service.network.RetrofitManager;

public class CommentListViewModel extends AndroidViewModel {

    private MutableLiveData<List<Comment>> commentListObservableLocal = new MutableLiveData<>();
    private MutableLiveData<List<Comment>> commentListObservableNetwork = new MutableLiveData<>();

    public CommentListViewModel(@NonNull Application application) {
        super(application);
    }

    public List<Comment> loadDataInfo(String postId) {
        List<Comment> comments =
                AppDatabase.getInstance(this.getApplication()).commentDao().getCommentsByPostId(Integer.parseInt(postId));
        commentListObservableLocal.setValue(comments);
        return comments;
    }

    public void requestNetwork(String postId) {
        commentListObservableNetwork =
                RetrofitManager.getInstance(this.getApplication()).getCommentListByPostId(postId);
    }

    public LiveData<List<Comment>> getCommentListObservable() {
        return commentListObservableNetwork;
    }

    public LiveData<List<Comment>> getCommentListObservableLocal() {
        return commentListObservableLocal;
    }
}
