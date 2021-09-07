package co.nz.demo.showposts.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import co.nz.demo.showposts.R;
import co.nz.demo.showposts.databinding.FragmentPostDetailBinding;
import co.nz.demo.showposts.service.model.Comment;
import co.nz.demo.showposts.service.model.Post;
import co.nz.demo.showposts.service.utils.Utils;
import co.nz.demo.showposts.view.adapter.CommentListAdapter;
import co.nz.demo.showposts.viewmodel.CommentListViewModel;
import co.nz.demo.showposts.viewmodel.PostViewModel;

public class PostFragment extends LifecycleFragment {

    public static final String TAG = "PostFragment";
    private static final String KEY_POST_ID = "post_id";
    private FragmentPostDetailBinding mFragmentPostDetailBinding;
    private PostViewModel mPostViewModel;
    private CommentListViewModel mCommentListViewModel;
    private CommentListAdapter mCommentListAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragmentPostDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_detail,
                container, false);

        mCommentListAdapter = new CommentListAdapter();
        mFragmentPostDetailBinding.commentList.setAdapter(mCommentListAdapter);

        mFragmentPostDetailBinding.setIsLoading(true);

        return mFragmentPostDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PostViewModel.Factory factory = new PostViewModel.Factory(
                getActivity().getApplication(), getArguments().getString(KEY_POST_ID));

        mPostViewModel = ViewModelProviders.of(this, factory)
                .get(PostViewModel.class);

        mCommentListViewModel =
                ViewModelProviders.of(this).get(CommentListViewModel.class);

        mPostViewModel.loadDataInfo(getArguments().getString(KEY_POST_ID));
        observeViewModelLocal(mPostViewModel);

        mPostViewModel.requestSearchPost(getArguments().getString(KEY_POST_ID));
        observeViewModelNetwork(mPostViewModel);

        mCommentListViewModel.loadDataInfo(getArguments().getString(KEY_POST_ID));
        observeCommentListViewModelLocal(mCommentListViewModel);

        mCommentListViewModel.requestNetwork(getArguments().getString(KEY_POST_ID));
        observeCommentListViewModelNetwork(mCommentListViewModel);
    }

    private void observeCommentListViewModelLocal(CommentListViewModel viewModel) {
        viewModel.getCommentListObservableLocal().observe(this.getViewLifecycleOwner(), new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> comments) {
                if (comments != null) {
                    mCommentListAdapter.setCommentList(comments);
                }
            }
        });
    }

    private void observeCommentListViewModelNetwork(CommentListViewModel viewModel) {
        viewModel.getCommentListObservable().observe(this.getViewLifecycleOwner(), new Observer<List<Comment>>() {
            @Override
            public void onChanged(@Nullable List<Comment> comments) {
                if (comments != null) {
                    mCommentListAdapter.setCommentList(comments);
                }
            }
        });
    }


    private void observeViewModelNetwork(final PostViewModel viewModel) {
        viewModel.getmPostObservableNetwork().observe(this.getViewLifecycleOwner(), new Observer<Post>() {
            @Override
            public void onChanged(@Nullable Post post) {
                Utils.printPostInfo(post);
                if (post != null) {
                    mFragmentPostDetailBinding.setIsLoading(false);
                    mFragmentPostDetailBinding.setPost(post);
                }
            }
        });
    }

    private void observeViewModelLocal(final PostViewModel viewModel) {
        viewModel.getmPostObservableLocal().observe(this.getViewLifecycleOwner(), new Observer<Post>() {
            @Override
            public void onChanged(@Nullable Post post) {
                Utils.printPostInfo(post);//打日志
                if (post != null) {
                    mFragmentPostDetailBinding.setIsLoading(false);
                    mFragmentPostDetailBinding.setPost(post);
                }
            }
        });
    }

    public static PostFragment forPost(String postID) {
        PostFragment fragment = new PostFragment();
        Bundle args = new Bundle();

        args.putString(KEY_POST_ID, postID);
        fragment.setArguments(args);
        return fragment;
    }

}
