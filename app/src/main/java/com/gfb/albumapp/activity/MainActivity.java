package com.gfb.albumapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.gfb.albumapp.R;
import com.gfb.albumapp.adapter.UserAdapter;
import com.gfb.albumapp.entity.User;
import com.gfb.albumapp.service.UserService;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, UserAdapter.Callback {

    private RecyclerView recyclerView;
    private TextView tvName;
    private TextView tvEmail;
    private TextView headerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(layoutManager);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        headerEmail = header.findViewById(R.id.tvEmail);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUsers();
    }

    private void getUsers() {
        User user = UserService.findUserByEmail(UserService.getLocalUser(this));
        if (user != null) {
            headerEmail.setText(user.getEmail());
            tvEmail.setText(user.getEmail());
            tvName.setText(user.getName());
            UserAdapter adapter = new UserAdapter(UserService.getUsers(), this, this);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            showHome();
        } else if (id == R.id.nav_gallery) {
            showAlbums();
        } else if (id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void showHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void showAlbums() {
        Intent intent = new Intent(this, AlbumsActivity.class);
        startActivity(intent);
    }

    private void logout() {
        UserService.saveLocalUser("", this);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClickItem(User user) {
        Intent intent = new Intent(this, RegisterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onClickDelete(User user) {
        ConfirmartionDialog confirmartionDialog = new ConfirmartionDialog();
        confirmartionDialog.setUser(user);
        confirmartionDialog.setCallback(onClickYes());
        confirmartionDialog.show(getSupportFragmentManager(), "dialog");
    }

    private ConfirmartionDialog.DialogCallback onClickYes() {
        return new ConfirmartionDialog.DialogCallback() {
            @Override
            public void onClickYes(User user) {
                User.delete(user);

                if (UserService.getUsers().size() == 0) {
                    showMessage(getString(R.string.disclaimer_no_users));
                    logout();
                } else {
                    getUsers();
                }
            }
        };
    }

    public static class ConfirmartionDialog extends DialogFragment {
        DialogCallback callback;
        User user;

        public void setUser(User user) {
            this.user = user;
        }

        public void setCallback(DialogCallback callback) {
            this.callback = callback;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.disclamer_delete_user)
                    .setPositiveButton(R.string.label_yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            callback.onClickYes(user);
                        }
                    })
                    .setNegativeButton(R.string.label_no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            return builder.create();
        }

        public interface DialogCallback {
            void onClickYes(User user);
        }
    }
}
