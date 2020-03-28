package chapter.android.aweme.ss.com.homework;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;



public class win extends AppCompatActivity {
    private TextView tv_win;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);
        Log.d("XingXiaoLin","成功跳转到win3");
        tv_win = findViewById(R.id.tv_win);

        Intent intent = getIntent();
        String text = intent.getStringExtra("index");
        Log.d("XingXiaoLin", text);
        tv_win.setText(text);
    }
}
