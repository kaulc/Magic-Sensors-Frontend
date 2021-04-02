package com.example.magic_sensors;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.mime.Header;

import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    Button getVersion;
    TextView displayVersion;

    private String BASE_URL = "https://asdf"; // *** CHANGE ***

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        getVersion = findViewById(R.id.getVersion);
        displayVersion = findViewById(R.id.displayVersion);

        getVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVersion();
            }
        });
    }

    private void getVersion(){
        String url = BASE_URL + "/debug/get/version";

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {

                // Log.d("Version", "JSON: " + response.toString());

                try {

                    // 'version' = field in JSON object
                    String version = response.getString("version");

                    displayVersion.setText(version);

                } catch (Exception e) {

                    Log.e("Version", e.toString());

                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e,
                                  JSONObject response) {


                // Log.d("Version", "Request fail! Status code: " + statusCode);
                // Log.d("Version", "Fail response: " + response);
                // Log.e("ERROR", e.toString());

                Toast.makeText(MainActivity.this, "Request Failed",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}