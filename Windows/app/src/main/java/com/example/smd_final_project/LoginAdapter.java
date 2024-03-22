package com.example.smd_final_project;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class LoginAdapter extends FragmentPagerAdapter {

    int totalTabs;
    private Context context;

    public LoginAdapter(@NonNull FragmentManager fm, int totalTabs, Context context) {
        super(fm);
        this.totalTabs = totalTabs;
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Toast.makeText(context.getApplicationContext(), "Login clicked"+position, Toast.LENGTH_SHORT).show();
                return new LoginFragment();
            case 1:
                Toast.makeText(context.getApplicationContext(), "signin clicked"+position, Toast.LENGTH_SHORT).show();
                return new SigninFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
