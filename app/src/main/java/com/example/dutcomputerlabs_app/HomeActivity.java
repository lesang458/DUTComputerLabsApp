package com.example.dutcomputerlabs_app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;


import com.example.dutcomputerlabs_app.apdaters.NotificationAdapter;
import com.example.dutcomputerlabs_app.models.NotificationForDetailed;
import com.example.dutcomputerlabs_app.utils.ApiUtils;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_user_detail, R.id.nav_change_password, R.id.nav_booking,R.id.nav_booking_history)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        View header = navigationView.getHeaderView(0);
        SharedPreferences pref = getSharedPreferences("PREF",MODE_PRIVATE);
        TextView text_name = header.findViewById(R.id.text_name);
        text_name.setText("Welcome, "+pref.getString("username",""));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_logout:
                        pref.edit().remove("token").apply();
                        pref.edit().remove("username").apply();
                        pref.edit().remove("password").apply();
                        pref.edit().remove("id").apply();

                        Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                        HomeActivity.this.finish();
                        break;
                    case R.id.nav_user_detail:
                        navController.navigate(R.id.nav_user_detail);
                        break;
                    case R.id.nav_change_password:
                        navController.navigate(R.id.nav_change_password);
                        break;
                    case R.id.nav_booking:
                        navController.navigate(R.id.nav_booking);
                        break;
                    case R.id.nav_booking_history:
                        navController.navigate(R.id.nav_booking_history);
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.notification:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("Thông báo");

                View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.notification_dialog, null);
                RecyclerView recyclerView = view.findViewById(R.id.recyclerview_notification);
                TextView text_notice = view.findViewById(R.id.text_notice);

                String token = getSharedPreferences("PREF",MODE_PRIVATE).getString("token","");
                ApiUtils.getNotificationService().getNotifications(token)
                        .enqueue(new Callback<List<NotificationForDetailed>>() {
                            @Override
                            public void onResponse(Call<List<NotificationForDetailed>> call, Response<List<NotificationForDetailed>> response) {
                                if(response.isSuccessful()) {
                                    if(response.body() == null) {
                                        text_notice.setText("Không có thông báo nào!");
                                    } else {
                                        ((ViewGroup) text_notice.getParent()).removeView(text_notice);
                                        List<NotificationForDetailed> notificationForDetailedList = new ArrayList<>(response.body());
                                        NotificationAdapter notificationAdapter = new NotificationAdapter(HomeActivity.this,notificationForDetailedList);
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
                                        recyclerView.setAdapter(notificationAdapter);
                                        recyclerView.setLayoutManager(linearLayoutManager);
                                        notificationAdapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<List<NotificationForDetailed>> call, Throwable t) {

                            }
                        });

                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setView(view);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                Button cancel = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                cancel.setBackground(getDrawable(R.drawable.border_button));
                cancel.setTextSize(20);
                cancel.setTextColor(Color.BLACK);

                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        return;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        SharedPreferences pref = getSharedPreferences("PREF",MODE_PRIVATE);
        pref.edit().remove("token").apply();
        pref.edit().remove("username").apply();
        pref.edit().remove("password").apply();
        pref.edit().remove("id").apply();
        super.onDestroy();
    }
}
