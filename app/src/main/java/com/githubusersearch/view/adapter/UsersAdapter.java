package com.githubusersearch.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.githubusersearch.R;
import com.githubusersearch.network.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ankit on 21/01/18.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.GenericViewHolder> {
    private List<User> usersList = new ArrayList<>();
    private Context context;

    public UsersAdapter(Context context) {
        this.context = context;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.itemView.setTag(usersList.get(position));
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static abstract class GenericViewHolder extends RecyclerView.ViewHolder {

        public GenericViewHolder(View rowView) {
            super(rowView);
        }

        protected abstract void bindView(int position);
    }

    class ContactViewHolder extends GenericViewHolder {

        @BindView(R.id.header_text) TextView header;
        @BindView(R.id.avtar_shimmer_view) ShimmerFrameLayout imageShimmerView;
        @BindView(R.id.avtar_image) ImageView avtar;
         ContactViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        protected void bindView(int position) {
            User user = (User) this.itemView.getTag();
            header.setText(String.format("@%s", user.getLogin()));
            if (!imageShimmerView.isAnimationStarted()) {
                imageShimmerView.startShimmerAnimation();
                try {
                    avtar.setBackgroundColor(context.getResources().getColor(R.color.shimmer_loader_color));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            Glide.with(context).load(user.getAvatar_url()).apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            stopAnimation(imageShimmerView, avtar);
                            if (avtar != null) {
                                //setting default placeholder image as we got exception while loading card photo
                                avtar.setBackground(ContextCompat.getDrawable(context, R.drawable.error_black));
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            stopAnimation(imageShimmerView, avtar);
                            return false;
                        }
                    })
                    .into(avtar);
        }
    }

    public void updateData(List<? extends User> newData) {
        usersList.clear();
        if (newData != null) {
            usersList.addAll(newData);
        }
        notifyDataSetChanged();
    }

    /**
     * this method will stop shimmer loading animation and sets image background as transparent on Card image
     * It should be called after setting image or in case of exception
     */
    private void stopAnimation(ShimmerFrameLayout imageShimmerView, ImageView avtar) {
        if (imageShimmerView.isAnimationStarted()) {
            imageShimmerView.stopShimmerAnimation();
        }

        avtar.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
    }
}
