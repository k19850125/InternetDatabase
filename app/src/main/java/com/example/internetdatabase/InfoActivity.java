package com.example.internetdatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    TextView id, name, phone;
    Button back, del, edit;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        findView();
        setListener();
        Intent intent = getIntent();
        index = intent.getIntExtra("id", -1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = db.query("phone", new String[]{"id", "username", "tel"}, "id=?", new String[]{String.valueOf(index)}, null, null, null);
        Log.d("info", String.valueOf(index));
        if (cursor.moveToFirst()) {
            id.setText(String.valueOf(cursor.getInt(0)));
            name.setText(cursor.getString(1));
            phone.setText(cursor.getString(2));
        }

    }


    private void findView() {
        id = (TextView) findViewById(R.id.id);
        name = (TextView) findViewById(R.id.name);
        phone = (TextView) findViewById(R.id.phone);
        back = (Button) findViewById(R.id.back);
        del = (Button) findViewById(R.id.delete);
        edit = (Button) findViewById(R.id.edit);
    }

    private void setListener() {
        back.setOnClickListener(listener);
        del.setOnClickListener(listener);
        edit.setOnClickListener(listener);
    }

    Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (back == v) {
                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (del == v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                builder.setTitle("Delete Confirm");
                builder.setMessage("Please Confirm the delete action");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = SQLiteDatabase.openDatabase(DB.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
                        db.delete("phone", "id=?", new String[]{String.valueOf(index)});
                        Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.show();
            } else if (edit == v) {
                Intent intent = new Intent(InfoActivity.this, EditActivity.class);
                intent.putExtra("id", index);
                startActivity(intent);
               
            }
        }
    };
}
