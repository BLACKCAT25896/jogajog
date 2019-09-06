package com.example.chattingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chattingapp.adapter.ViewPagweAdapter;
import com.example.chattingapp.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpViewPager(binding.viewPager);
        binding.tabs.setupWithViewPager(binding.viewPager);


    }

    private void setUpViewPager(ViewPager viewPager) {


        ViewPagweAdapter adapter = new ViewPagweAdapter(getSupportFragmentManager());
        adapter.addFragment(ProfileFragment.getInstance(), "Profile");
        adapter.addFragment(ChatFragment.getInstance(), "Chat");
        adapter.addFragment(ContactFragment.getInstance(), "Contacts");


        viewPager.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {

            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, SignInActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }
}
