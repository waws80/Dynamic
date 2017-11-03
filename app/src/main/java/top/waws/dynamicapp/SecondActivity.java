package top.waws.dynamicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import pw.androidthanatos.annotation.Path;

@Path("second")
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = new Intent();
        intent.putExtra("tag","second");
        setResult(1000,intent);
    }
}
