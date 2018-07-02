package com.example.harshit.searchbarlv;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // List view
    private ListView lv;
   // Listview Adapter
    ArrayAdapter<GOTChar> adapter;

    // Search EditText
    EditText inputSearch;


    Button but;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        but=(Button)findViewById(R.id.search);
        String url = "https://api.got.show/api/characters";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest

                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        //mTextView.setText("Response: " + response.toString());


                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject json_data = response.getJSONObject(i);
                                //int id = json_data.getInt("id");
                                GOTChar gotchar=new GOTChar();
                                gotchar.name = json_data.getString("name");
                                gotchar.housename = json_data.getString("house");
                                gotchar.pageRank = json_data.getString("pageRank");
                                gotchar.books=  json_data.getString("books");
                              try{  gotchar.imageLink=json_data.getString("imageLink");
                              } catch (Exception exp) {
                              }
                                  MyGlobals.members.add(gotchar);

                            } catch (Exception exp) {
                            }
                        }

                        lv = (ListView) findViewById(R.id.lv);

                        // Adding items to listview
                        adapter = new ArrayAdapter<GOTChar>(MainActivity.this,
                                android.R.layout.simple_list_item_1, MyGlobals.members);
                        lv.setAdapter(adapter);
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            Intent i2=new Intent(MainActivity.this,attributes.class);
                             Bundle extras=new Bundle();
                             //extras.putString("name",name);

                                gotchar=(GOTChar)lv.getItemAtPosition(position);
                                extras.putSerializable("gotchar", gotchar );

                                savetohistory(null);

                              i2.putExtras(extras);
                                startActivity(i2);


                            }
                        });
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });


        queue.add(jsonArrayRequest);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                if(MainActivity.this.adapter!=null) {
                    MainActivity.this.adapter.getFilter().filter(cs);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
             case R.id.history:
                showhistory();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
GOTChar gotchar;
    public  void savetohistory(View v){

        DatabaseTable db=new DatabaseTable(this);
        db
            .addWord(gotchar.name
                    ,"jhgf");

    }

     public void showhistory (){
        Intent i=new Intent(this,data.class);
        startActivity(i);
     }

    }