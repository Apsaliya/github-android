package com.githubusersearch.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.contactlist.R;
import com.contactlist.network.model.Contact;
import com.contactlist.view.callback.ContactClickCallback;
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

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
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

    static class ContactViewHolder extends GenericViewHolder {

        @BindView(R.id.header_text) TextView header;
        @BindView(R.id.subheader_text) TextView subHeader;
        @BindView(R.id.info_text) TextView info;

         ContactViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        protected void bindView(int position) {
            User contact = (User) this.itemView.getTag();
        }
    }

    public void updateData(List<? extends User> newData) {
        usersList.clear();
        usersList.addAll(newData);
        notifyDataSetChanged();
    }
}
