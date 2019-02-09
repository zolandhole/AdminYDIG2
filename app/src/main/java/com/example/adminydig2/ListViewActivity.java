package com.example.adminydig2;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminydig2.adapter.PhotoAdapter;
import com.example.adminydig2.model.PhotoModel;
import com.example.adminydig2.util.EndPoints;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private PhotoAdapter photoAdapter;
    private RecyclerView recyclerView;
    private TextView textViewNophoto;
    ArrayList<PhotoModel> photoModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        recyclerView = findViewById(R.id.recycler);
        textViewNophoto = findViewById(R.id.textViewNophoto);
        progressDialog = new ProgressDialog(this , R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Mohon Menunggu...");
        progressDialog.setCanceledOnTouchOutside(false);
        toolBar();

        mengambilData();
        findViewById(R.id.buttonAddPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListViewActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        textViewNophoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mengambilData();
            }
        });
    }

    private void toolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Admin Panel");
        toolbar.setSubtitle("YDIG");
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("YDIG");
    }

    private void mengambilData() {
        textViewNophoto.setVisibility(View.GONE);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndPoints.GETIKLAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Log.d("strrrrr", ">>" + response);
                        try {
                            progressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            if(obj.optString("error").equals("false")){
                                textViewNophoto.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                photoModels = new ArrayList<>();
                                JSONArray dataArray  = obj.getJSONArray("message");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    PhotoModel model = new PhotoModel();
                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    model.setCaption(dataobj.getString("caption"));
                                    model.setTampilkansampai(dataobj.getString("tampilkansampai"));
                                    model.setStatusphoto(dataobj.getString("statusphoto"));
                                    model.setImgURL(dataobj.getString("photo"));
                                    photoModels.add(model);
                                }
                                setupRecycler();
                            } else if (obj.optString("message").equals("Belum ada Photo terupload")){
                                textViewNophoto.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        textViewNophoto.setText("Kesalahan Koneksi, klik ulangi");
                        textViewNophoto.setVisibility(View.VISIBLE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void setupRecycler() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        photoAdapter = new PhotoAdapter(this, photoModels);
        LinearLayoutManager manager = (new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(photoAdapter);
    }
}
