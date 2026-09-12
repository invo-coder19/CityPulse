package com.example.cityhealth;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etRegName, etRegEmail, etRegPassword, etRegCity;
    private Button btnRegister, btnBackToLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize views
        etRegName = findViewById(R.id.etRegName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegCity = findViewById(R.id.etRegCity);
        btnRegister = findViewById(R.id.btnRegister);
        btnBackToLogin = findViewById(R.id.btnBackToLogin);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Register button click listener
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etRegName.getText() != null ? etRegName.getText().toString().trim() : "";
                String email = etRegEmail.getText() != null ? etRegEmail.getText().toString().trim().toLowerCase() : "";
                String password = etRegPassword.getText() != null ? etRegPassword.getText().toString().trim() : "";
                String city = etRegCity.getText() != null ? etRegCity.getText().toString().trim() : "";

                // Validate fields
                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || city.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 4) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 4 characters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if user already exists
                String existingPassword = sharedPreferences.getString(email + "_password", "");
                if (!existingPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "User already exists with this email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save user data
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(email + "_password", password);
                editor.putString(email + "_name", name);
                editor.putString(email + "_city", city);
                editor.apply();

                Toast.makeText(RegisterActivity.this, "Registration successful! Please login now.", Toast.LENGTH_LONG).show();

                // Go back to login screen
                finish();
            }
        });

        // Back to login button click listener
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
