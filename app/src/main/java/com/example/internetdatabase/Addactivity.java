package com.example.internetdatabase;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Addactivity extends AppCompatActivity {

    EditText phone, name;
    Button add, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addactivity);
        findView();
        add.setOnClickListener(listener);
        cancel.setOnClickListener(listener);

    }

    private void findView() {
        phone = (EditText) findViewById(R.id.name);
        name = (EditText) findViewById(R.id.name);
        add = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
    }

    Button.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (add == v) {
                SQLiteDatabase db = SQLiteDatabase.openDatabase(DB.DB_FILE, null, SQLiteDatabase.OPEN_READWRITE);
                String username = name.getText().toString();
                String tel = phone.getText().toString();
                String str = "Insert Into phone (username, tel) values ('" + username + "', '" + tel + "')";
                db.execSQL(str);
                Intent intent = new Intent(Addactivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            else if (cancel == v){
                phone.setText("");
                name.setText("");
                Intent intent = new Intent(Addactivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
