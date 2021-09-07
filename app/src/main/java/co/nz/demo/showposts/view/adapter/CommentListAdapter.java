package co.nz.demo.showposts.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.nz.demo.showposts.R;
import co.nz.demo.showposts.databinding.CommentListItemBinding;
import co.nz.demo.showposts.service.model.Comment;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.CommentViewHolder> {

    List<? extends Comment> mCommentList;

    public CommentListAdapter() {

    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentListItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_list_item,
                        parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.binding.setComment(mCommentList.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCommentList == null ? 0 : mCommentList.size();
    }

    public void setCommentList(final List<? extends Comment> commentList) {
        if (this.mCommentList == null) {
            this.mCommentList = commentList;
            notifyItemRangeInserted(0, commentList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return CommentListAdapter.this.mCommentList.size();
                }

                @Override
                public int getNewListSize() {
                    return commentList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return CommentListAdapter.this.mCommentList.get(oldItemPosition).id
                            == commentList.get(newItemPosition).id;
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Comment newComment = commentList.get(newItemPosition);
                    Comment oldComment = CommentListAdapter.this.mCommentList.get(oldItemPosition);
                    return (newComment.id == oldComment.id) && newComment.body.equals(oldComment.body);
                }
            });

            this.mCommentList = commentList;
            result.dispatchUpdatesTo(this);
        }
    }

    /**
     * holder class
     */
    static class CommentViewHolder extends RecyclerView.ViewHolder {

        final CommentListItemBinding binding;

        public CommentViewHolder(CommentListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
