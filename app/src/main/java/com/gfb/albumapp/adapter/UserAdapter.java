package com.gfb.albumapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gfb.albumapp.R;
import com.gfb.albumapp.entity.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users;
    private Context context;
    private Callback callback;

    public UserAdapter(List<User> users, Context context, Callback callback) {
        this.users = users;
        this.context = context;
        this.callback = callback;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = users.get(position);
        if (user != null) {
            holder.tvEmail.setText(String.valueOf(user.getEmail()));
            holder.tvName.setText(String.valueOf(user.getName()));
            holder.btDelete.setOnClickListener(onClickDelete(user));
            holder.rootView.setOnClickListener(onClickItem(user));

        }
    }

    private View.OnClickListener onClickDelete(final User user) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickDelete(user);
            }
        };
    }

    private View.OnClickListener onClickItem(final User user) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickItem(user);
            }
        };
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvEmail;
        private ImageView btDelete;
        private LinearLayout rootView;

        UserViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            btDelete = itemView.findViewById(R.id.btDelete);
            rootView = itemView.findViewById(R.id.rootView);
        }
    }

    public interface Callback {
        void onClickItem(User user);

        void onClickDelete(User user);
    }

}
