package com.baekcedar.android.notepad;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by HM on 2016-09-30.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<RecyclerData> datas;
    int itemLayout;
    MainActivity context;
    RecyclerData data;



    //생성자
    public RecyclerAdapter(ArrayList<RecyclerData> datas, int itemLayout, MainActivity context){
        this.datas = datas;
        this.itemLayout = itemLayout;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        data = datas.get(position);

       holder.carView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {    //롱클릭

                context.delete(position);

                return true; //true 이면 Click 처리 안됨.
            }
        });

        holder.carView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //클릭시 수정모드

                context.updateMode(position);
                context.viewMode(2);

            }
        });

        String setTitle;
        if(data.title.length() > 30){ //30 자 이상 처리
            setTitle = (position+1)+". "+data.title.substring(0, 30);
        }else{
            setTitle = (position+1)+". "+data.title;
        }
        holder.textTitle.setText(setTitle);
        holder.itemView.setTag(data);

        setAnimation(holder.carView, position);
    }
    int lastPosision = -1;
    public void setAnimation(View view,int position){
        if(position > lastPosision) {
            Animation ani = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            view.startAnimation(ani);
            lastPosision = position;
        }
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        CardView carView;
        public ViewHolder(View itemView) {
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.carTextTitle);

            carView = (CardView) itemView.findViewById(R.id.cardItem);
        }

    }
}
