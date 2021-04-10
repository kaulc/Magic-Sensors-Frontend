package com.example.magic_sensors.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.magic_sensors.MainActivity;
import com.example.magic_sensors.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.mime.Header;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    Button btn_getVersion;
    TextView lbl_version;
    private String BASE_URL = "http://10.29.163.209:8118"; // *** CHANGE ***

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        btn_getVersion = root.findViewById(R.id.btn_getVersion);
        lbl_version = root.findViewById(R.id.lbl_version);

        btn_getVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVersion(view);
            }
        });

        return root;
    }

    private void getVersion(View view){
        String url = BASE_URL + "/debug/get/version";

        RequestQueue queue = Volley.newRequestQueue(view.getContext());

        StringRequest sr = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                lbl_version.setText(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
                lbl_version.setText("No Response :(");
            }
        });

        queue.add(sr);
    }
}