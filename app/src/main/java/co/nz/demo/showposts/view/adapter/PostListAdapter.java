package co.nz.demo.showposts.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.nz.demo.showposts.R;
import co.nz.demo.showposts.databinding.PostListItemBinding;
import co.nz.demo.showposts.service.model.Post;
import co.nz.demo.showposts.view.callback.PostClickCallBack;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {
    List<? extends Post> mPostList;

    private final PostClickCallBack mPostClickCallback;
    public PostListAdapter(PostClickCallBack postClickCallback) {
        this.mPostClickCallback = postClickCallback;
    }

    @NonNull
    @Override
    public PostListAdapter.PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.post_list_item,
                        parent, false);

        binding.setCallback(mPostClickCallback);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.PostViewHolder holder, int position) {
        holder.binding.setPost(mPostList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mPostList == null ? 0 : mPostList.size();
    }

    /**
     * Provide to external calls to update the data source and notify UI
     * @param postList
     */
    public void setPostList(final List<? extends Post> postList) {
        if (this.mPostList == null) {
            this.mPostList = postList;
            notifyItemRangeInserted(0, postList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return PostListAdapter.this.mPostList.size();
                }

                @Override
                public int getNewListSize() {
                    return postList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return PostListAdapter.this.mPostList.get(oldItemPosition).title.
                            equals(postList.get(newItemPosition).title);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Post newPost = postList.get(newItemPosition);
                    Post oldPost = PostListAdapter.this.mPostList.get(oldItemPosition);
                    return newPost.title.equals(oldPost.title)
                            && newPost.body.equals(oldPost.body);
                }
            });

            this.mPostList = postList;
            result.dispatchUpdatesTo(this);
        }
    }


    /**
     * holder class
     */
    static class PostViewHolder extends RecyclerView.ViewHolder {

        final PostListItemBinding binding;

        public PostViewHolder(PostListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
