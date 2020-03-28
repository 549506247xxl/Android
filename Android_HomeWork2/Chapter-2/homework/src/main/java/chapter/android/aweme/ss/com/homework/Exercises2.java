package chapter.android.aweme.ss.com.homework;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * 作业2：一个抖音笔试题：统计页面所有view的个数
 * Tips：ViewGroup有两个API
 * {@link android.view.ViewGroup #getChildAt(int) #getChildCount()}
 * 用一个TextView展示出来
 */
public class Exercises2 extends AppCompatActivity implements View.OnClickListener{
    public TextView tv; // 声明TextView
    public Button btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises2); //设置ex2的布局
        tv = findViewById(R.id.tv2);
        btn = findViewById(R.id.ex2_btn);
        btn.setOnClickListener(this);

    }

    public int getAllChildViewCount(View view) {
        //todo 补全你的代码
        int res = 0;     // 返回值
        if(view instanceof ViewGroup) //当前View是ViewGroup
        {
            int num = ((ViewGroup) view).getChildCount(); //获得当前ViewGroup的view数
            for(int i=0; i<num;i++)
            {
                View v = ((ViewGroup) view).getChildAt(i);
                res += getAllChildViewCount(v); //递归地计算当前View下的孩子数
            }
            return res;
        }
        else{ //单个组件View
            return 1;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ex2_btn:
                tv.setVisibility(View.VISIBLE);
                //调用获得view函数
                String ans = Integer.toString(getAllChildViewCount(findViewById(R.id.linear)));
                Log.d("Ex2","view numbers:"+ans);
                tv.setText(ans);
                break;
        }
    }
}
