package com.example.internetdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {
    EditText name, phone;
    Button clear, save;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        save = (Button) findViewById(R.id.save);
        clear = (Button) findViewById(R.id.clear);
        Intent intent = getIntent();
        index = intent.getIntExtra("id", -1);
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = db.query("phone", new String[]{"id", "username", "tel"}, "id=?", new String[]{String.valueOf(index)}, null, null, null);
        if (cursor.moveToFirst()) {
            name.setText(cursor.getString(1));
            phone.setText(cursor.getString(2));
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = SQLiteDatabase.openDatabase(DB.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
                ContentValues cv = new ContentValues();
                cv.put("username", name.getText().toString());
                cv.put("tel", phone.getText().toString());
                db.update("phone", cv, "id=?", new String[]{String.valueOf(index)});
                db.close();
                finish();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
