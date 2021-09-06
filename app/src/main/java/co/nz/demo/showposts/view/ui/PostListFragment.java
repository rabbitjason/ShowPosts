package co.nz.demo.showposts.view.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import co.nz.demo.showposts.R;
import co.nz.demo.showposts.databinding.FragmentPostListBinding;
import co.nz.demo.showposts.service.model.Post;
import co.nz.demo.showposts.service.utils.Utils;
import co.nz.demo.showposts.view.adapter.PostListAdapter;
import co.nz.demo.showposts.view.callback.PostClickCallBack;
import co.nz.demo.showposts.viewmodel.PostListViewModel;

public class PostListFragment extends LifecycleFragment {

    public static final String TAG = "PostListFragment";
    private PostListAdapter mPostListAdapter;
    private FragmentPostListBinding mFragmentPostListBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mFragmentPostListBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_post_list, container, false);

        mPostListAdapter = new PostListAdapter(mPostClickCallBack);

        mFragmentPostListBinding.postList.setAdapter(mPostListAdapter);
        mFragmentPostListBinding.setIsLoading(true);
        return mFragmentPostListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initial viewModel
        final PostListViewModel viewModel =
                ViewModelProviders.of(this).get(PostListViewModel.class);

        // Get the posts from the local or catch
        viewModel.loadDataInfo();
        observeViewModelLocal(viewModel);

        // Request the posts by network
        viewModel.requestNetwork();
        observeViewModelNetwork(viewModel);
    }


    /**
     * Observer callback listener: after reading DB or Cache, refreshing UI
     * @param viewModel
     */
    private void observeViewModelLocal(PostListViewModel viewModel) {
        viewModel.getPostListObserableLocal().observe(this.getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                Utils.printPostInfo(posts);
                if (posts != null) {
                    mFragmentPostListBinding.setIsLoading(false);
                    mPostListAdapter.setPostList(posts);
                }
            }
        });
    }

    /**
     * Observer callback listener: after getting the post by network, refreshing UI
     * @param viewModel
     */
    private void observeViewModelNetwork(PostListViewModel viewModel) {
        viewModel.getPostListObserable().observe(this.getViewLifecycleOwner(), new Observer<List<Post>>() {
            @Override
            public void onChanged(@Nullable List<Post> posts) {
                Utils.printPostInfo(posts);
                if (posts != null) {
                    mFragmentPostListBinding.setIsLoading(false);
                    mPostListAdapter.setPostList(posts);
                }
            }
        });
    }

    private PostClickCallBack mPostClickCallBack = new PostClickCallBack() {
        @Override
        public void onClick(Post post) {
            if (getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                ((MainActivity) getActivity()).show(post);
            }
        }
    };
}
