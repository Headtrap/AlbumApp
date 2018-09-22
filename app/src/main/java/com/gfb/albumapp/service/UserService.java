package com.gfb.albumapp.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.gfb.albumapp.entity.User;

import java.util.List;

public class UserService {
    private static SharedPreferences sharedpreferences;

    public static List<User> getUsers() {
        return User.listAll(User.class);
    }

    public static User findUserByEmail(String email) {
        List<User> users = getUsers();
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }

    public static void saveLocalUser(String userEmail, Context context) {
        sharedpreferences = context.getSharedPreferences("PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("KEY_EMAIL", userEmail);
        editor.apply();
    }

    public static String getLocalUser(Context context) {
        sharedpreferences = context.getSharedPreferences("PREF", Context.MODE_PRIVATE);
        return sharedpreferences.getString("KEY_EMAIL", "");
    }
}
