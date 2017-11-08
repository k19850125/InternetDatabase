package com.example.internetdatabase;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    ArrayAdapter<String> adapter;
    ArrayList<phone> mylist;
    ArrayList<String> showList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");
        listView = (ListView) findViewById(R.id.listView);
        showList= new ArrayList<>();
        mylist = new ArrayList<>();
        DB.DB_FILE = getFilesDir() + File.separator + "mydata.sqlite";
        copyDBFile();
        getInfo();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                intent.putExtra("id",mylist.get(position).id);
                startActivity(intent);
                finish();
            }
        });



    }

    private void getInfo() {
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice, showList);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mylist.clear();
        showList.clear();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
//                Cursor cursor = db.rawQuery("SELECT * FROM phone",null);
        Cursor cursor = db.query("phone", new String[]{"id", "username", "tel"}, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                mylist.add(new phone(cursor.getInt(0),cursor.getString(1),cursor.getString(2)));
                showList.add(cursor.getString(1) + "," + cursor.getString(2));
            } while (cursor.moveToNext());
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            Intent it = new Intent(MainActivity.this, Addactivity.class);
            startActivity(it);
            finish();
        }

        return super.onOptionsItemSelected(item);

    }

    public void copyDBFile() {
        try {
            File f = new File(DB.DB_FILE);
            if (!f.exists()) {
                InputStream is = getResources().openRawResource(R.raw.mydata);
                OutputStream os = new FileOutputStream(DB.DB_FILE);
                int read;
                while ((read = is.read()) != -1) {
                    os.write(read);
                }
                os.close();
                is.close();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
