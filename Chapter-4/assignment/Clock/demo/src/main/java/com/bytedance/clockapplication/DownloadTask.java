package com.bytedance.clockapplication;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.TextView;

@SuppressLint("CI_StaticFieldLeak")
public class DownloadTask extends AsyncTask<String, Integer, String> {
    TextView mTextView;

    DownloadTask(TextView textView) { //默认构造函数，参数textView
        mTextView = textView;
    }

    @Override
    protected void onPreExecute() {         // 执行之前的操作
        super.onPreExecute();
        mTextView.setText("开始下载....");
    }

    @Override
    protected String doInBackground(String... strings) {     // 后台进行的操作
        String url = strings[0];
        try {
            return downlaod(url);
        } catch (Exception e) {
            return "Fail";
        }
    }

    private String downlaod(String url) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            publishProgress(i);
            Thread.sleep(50);
        }
        return "Success";
    }

    @Override
    protected void onProgressUpdate(Integer... values) {  // 在主线程中进行，接受来自publishProgress传递的参数
        super.onProgressUpdate(values);
        mTextView.setText("下载进度：" + values[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        mTextView.setText("下载完成：" + s);
    }
}
