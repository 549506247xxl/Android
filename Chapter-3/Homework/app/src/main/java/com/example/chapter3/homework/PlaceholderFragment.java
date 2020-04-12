package com.example.chapter3.homework;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlaceholderFragment extends Fragment {
    private static int Item_NUM = 50;
    private ListView list;
    private LottieAnimationView mLottie;
    private ArrayList<Map<String, Object>> mData = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO ex3-3: 修改 fragment_placeholder，添加 loading 控件和列表视图控件
        View view = inflater.inflate(R.layout.fragment_placeholder, container, false);
        /*
        * 添加列表视图
        * */
        list = view.findViewById(R.id.list_view);
        getData();
        //通过简单适配器，将数据和xml结合起来
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), mData, R.layout.simple_item,
                new String[]{"name"}, new int[]{R.id.item_text});
        list.setAdapter(adapter); //设置适配器


        /*
        添加loading控件
         */
        mLottie = view.findViewById(R.id.fragment_animation_view);
        mLottie.playAnimation();

        list.setVisibility(View.INVISIBLE); //进入的时候设置列表不可见
        return view;
    }
    private void getData(){
        for(int i=0; i<Item_NUM; i++){
            HashMap<String, Object> item = new HashMap<>();
            item.put("name", "item"+i);
            mData.add(item);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 这里会在 5s 后执行
                // TODO ex3-4：实现动画，将 lottie 控件淡出，列表数据淡入

                //5s之后设置 lottie 动画不可见，列表可见
//                mLottie.setVisibility(View.INVISIBLE);
//                list.setVisibility(View.VISIBLE);
                AlphaAnimation alpha_1 = new AlphaAnimation(1f,0f);
                alpha_1.setDuration(1000);
                alpha_1.setFillAfter(true);

                AlphaAnimation alpha_2 = new AlphaAnimation(0f,1f);
                alpha_2.setDuration(1000);
                alpha_2.setFillAfter(true);

                mLottie.startAnimation(alpha_1);
                list.startAnimation(alpha_2);
            }
        }, 5000);
    }

}
