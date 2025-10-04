package com.example.cityhealth;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private Button btnBackHome;
    private TextView tvProfileTitle, tvUserName, tvUserEmail, tvUserCity;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        btnBackHome = findViewById(R.id.btnBackHome);
        tvProfileTitle = findViewById(R.id.tvProfileTitle);
        tvUserName = findViewById(R.id.tvUserName);
        tvUserEmail = findViewById(R.id.tvUserEmail);
        tvUserCity = findViewById(R.id.tvUserCity);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Load user data
        loadUserProfile();

        // Back to home button
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadUserProfile() {
        String currentUser = sharedPreferences.getString("current_user", "");
        String userName = sharedPreferences.getString(currentUser + "_name", "N/A");
        String userCity = sharedPreferences.getString(currentUser + "_city", "N/A");

        tvUserName.setText("Name: " + userName);
        tvUserEmail.setText("Email: " + currentUser);
        tvUserCity.setText("Home City: " + userCity);
    }
}