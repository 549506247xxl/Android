package chapter.android.aweme.ss.com.homework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.InputStream;
import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.model.PullParser;

/**
 * 大作业:实现一个抖音消息页面,
 * 1、所需的data数据放在assets下面的data.xml这里，使用PullParser这个工具类进行xml解析即可
 * <p>如何读取assets目录下的资源，可以参考如下代码</p>
 * <pre class="prettyprint">
 *
 *         @Override
 *     protected void onCreate(@Nullable Bundle savedInstanceState) {
 *         super.onCreate(savedInstanceState);
 *         setContentView(R.layout.activity_xml);
 *         //load data from assets/data.xml
 *         try {
 *             InputStream assetInput = getAssets().open("data.xml");
 *             List<Message> messages = PullParser.pull2xml(assetInput);
 *             for (Message message : messages) {
 *
 *             }
 *         } catch (Exception exception) {
 *             exception.printStackTrace();
 *         }
 *     }
 * </pre>
 * 2、所需UI资源已放在res/drawable-xxhdpi下面
 *
 * 3、作业中的会用到圆形的ImageView,可以参考 widget/CircleImageView.java
 */
public class Exercises3 extends AppCompatActivity {
    private static final String TAG = "Exercise3";
    private static final int NUM_LIST_ITEMS = 100;

    private RecyclerView mRecyclerView;
    private List<Message> news; //信息列表

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置当前activity为 activity_tips
        setContentView(R.layout.activity_tips);
        // 1、获取控件对象
        mRecyclerView =  findViewById(R.id.rv_list);
        Log.d(TAG,"获取控件对象");

        // 2、为RecyclerView 设置布局管理器
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //设置默认动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //自定义分割线的效果，需要自己实现ItemDecoration接口
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        mRecyclerView.setHasFixedSize(true);
        Log.d(TAG, "设置布局管理器");

        //读取数据，存储到List类型的news中
        try {
            InputStream assetInput = getAssets().open("data.xml");
            news = PullParser.pull2xml(assetInput);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

     //   System.out.println(news.get(0).toString());
        Log.d(TAG, "读取news成功");

        // 3、为RecyclerView 设置数据适配器
        MyAdapter myAdapter = new MyAdapter(news.size(), news); //适配器的内容是后台提供的news

        mRecyclerView.setAdapter(myAdapter);    //设置适配器

    }


    /*
        实现我们的点击事件myItemClick
     */
    public void myItemClick(View view){
        //获取myItemClick的位置
        int position = mRecyclerView.getChildAdapterPosition(view);

        // 跳转到新的界面win
        Intent intent = new Intent(this, win.class); //当前activity跳转到win
        intent.putExtra("index", "点击了页面"+position);
        Log.d("intent","实现了跳转"+position);
        startActivity(intent);
    //    Toast.makeText(this,"点击了"+position, Toast.LENGTH_SHORT).show();
    }

}
