package com.codingelab.tutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class phpsearch extends AppCompatActivity {
    EditText txtGVFU;
    Button btnfet_Data;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phpsearch);
        txtGVFU = (EditText)findViewById(R.id.editText);
        btnfet_Data = (Button)findViewById(R.id.buttonfetch);
        listview = (ListView)findViewById(R.id.listView);
        btnfet_Data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            } });
    }
    private void getData() {
        String value = txtGVFU.getText().toString().trim();
        if (value.equals("")) {
            Toast.makeText(this, "Here Enter Data Value, Pleas", Toast.LENGTH_LONG).show();
            return;
        }
        String url = c_phpSearch.DATA_URL + txtGVFU.getText().toString().trim();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(phpsearch.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        final ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(c_phpSearch.ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(c_phpSearch.ID);
                String Name = jo.getString(c_phpSearch.NAME);
                String Phone = jo.getString(c_phpSearch.PHONE);
                String Email = jo.getString(c_phpSearch.EMAIL);
                final HashMap<String, String> User = new HashMap<>();
                User.put(c_phpSearch.ID, "id: "+id);
                User.put(c_phpSearch.NAME, "name: "+Name);
                User.put(c_phpSearch.PHONE, "phone: "+Phone);
                User.put(c_phpSearch.EMAIL,  "email: "+Email);
                list.add(User);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                phpsearch.this, list, R.layout.listphp,
                new String[]{
                        c_phpSearch.ID,
                        c_phpSearch.NAME,
                        c_phpSearch.PHONE,
                        c_phpSearch.EMAIL},
                new int[]{
                        R.id.id,
                        R.id.name2,
                        R.id.phone2,
                        R.id.Email2,
                });
        listview.setAdapter(adapter);
    }
}