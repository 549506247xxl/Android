package chapter.android.aweme.ss.com.homework;

import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import chapter.android.aweme.ss.com.homework.model.Message;
import chapter.android.aweme.ss.com.homework.widget.CircleImageView;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "XingXiaoLin";

    private int mNumberItems;     //显示条项的数目
    private List<Message> news;

    private static int viewHolderCount;

    //构造函数
    public MyAdapter(int mNumberItems, List<Message> news){
        this.mNumberItems = mNumberItems;
        this.news = news;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.im_list_item, null, false);

        // RecyclerView.ViewHolder是个抽象类，我们需要继承这个类，实现自己的Holder
        RecyclerView.ViewHolder holder = new MyViewHolder(v);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        Message message = news.get(i);
        ((MyViewHolder) holder).title.setText(message.getTitle());
        ((MyViewHolder) holder).time.setText(message.getTime());
        ((MyViewHolder) holder).description.setText(message.getDescription());
        int IconID = 0;
        switch (message.getIcon()){
            case "TYPE_ROBOT":    IconID = R.drawable.session_robot; break;
            case "TYPE_GAME":     IconID = R.drawable.icon_micro_game_comment; break;
            case "TYPE_SYSTEM":   IconID = R.drawable.session_system_notice; break;
            case "TYPE_STRANGER": IconID = R.drawable.session_stranger; break;
            case "TYPE_USER":     IconID = R.drawable.icon_girl; break;

        }
        ((MyViewHolder) holder).icon.setImageResource(IconID);



        int vis = message.isOfficial()? ImageView.VISIBLE: ImageView.INVISIBLE;
        ((MyViewHolder) holder).isOfficial.setVisibility(vis);

    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    public TextView title;
    public TextView description;
    public TextView time;
    public ImageView isOfficial;
    public CircleImageView icon;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.tv_title);
        description = itemView.findViewById(R.id.tv_description);
        time = itemView.findViewById(R.id.tv_time);
        isOfficial = itemView.findViewById(R.id.robot_notice);
        icon = itemView.findViewById(R.id.iv_avatar);
    }

}