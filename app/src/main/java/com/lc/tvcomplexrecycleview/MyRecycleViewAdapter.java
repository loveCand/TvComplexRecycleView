package com.lc.tvcomplexrecycleview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */

public class MyRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> films;
    private int TOP = 100;
    private int MEDDO = 111;

    public MyRecycleViewAdapter(Context context, List<String> data) {
        this.context = context;
        this.films = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TOP) {
            View inflate = LayoutInflater.from(context).inflate(R.layout.recycle_top_item, null);
            return new TopViewHolder(inflate,10);//如果item等于0代表是第一个,就返回top布局
        }
        View inflate = LayoutInflater.from(context).inflate(R.layout.recycle_item, null);
        return new MyViewHolder(inflate);
    }

    public void clearDates() {
        this.films.clear();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (position ==0){
        }else {
            if (holder instanceof MyViewHolder){
                MyViewHolder myViewHolder = (MyViewHolder) holder;
                myViewHolder.rootView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        Log.d("lichao", "in rootView hasFocus is " + hasFocus);
                        if (hasFocus) {
                            v.setBackgroundColor(Color.parseColor("#FFE60E0E"));
                        } else {
                            v.setBackgroundColor(Color.parseColor("#FFF1EBEB"));
                        }
                    }
                });
                myViewHolder.rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("lichao", "in rootView onClick " + position);
                    }
                });
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP;
        }
        return MEDDO;
    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public LinearLayout rootView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            rootView = itemView.findViewById(R.id.root);
        }
    }
    class TopViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        private Animation scaleBigAnimation;
        public TopViewHolder(View itemView,int size ) {
            super(itemView);
            LinearLayout linearLayout = itemView.findViewById(R.id.linearr_top);
            linearLayout.removeAllViews();
            for (int i=0;i<10; i++){//top布局动态添加
                ImageView imageView = new ImageView(context);
                if (i==0){
                    Log.d("lichao","in for imageView is 0");
                }
                imageView.setId(100+i);
                imageView.setNextFocusRightId(100+i+1);
                imageView.setNextFocusLeftId(100+i-1);
                imageView.setImageResource(R.drawable.mouseover);
                imageView.setClickable(true);
                imageView.setFocusable(true);
                imageView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean hasFocus) {
                        if (hasFocus) {
                            Log.d("lichao","hasFocus ........true");
                            scaleBigAnimation = AnimationUtils.loadAnimation(context,R.anim.anim_scale_big_height);
                            view.startAnimation(scaleBigAnimation);
                        } else {
                            Log.d("lichao","hasFocus ........ mfalse");
                            ScaleAnimation animation = new ScaleAnimation(
                                    1.0f, 1.0f, 1.0f, 1.0f,
                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
                            );
                            animation.setDuration(3000);
                            view.startAnimation(animation);
                        }
                    }
                });
                LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10,10,10,10);
                imageView.setLayoutParams(layoutParams);
                linearLayout.addView(imageView);
            }
            linearLayout.getChildAt(0).requestFocus();//默认第一个获取焦点
        }
    }
}
