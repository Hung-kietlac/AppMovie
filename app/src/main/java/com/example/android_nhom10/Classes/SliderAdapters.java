package com.example.android_nhom10.Classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.android_nhom10.R;

import java.util.List;

public class SliderAdapters extends RecyclerView.Adapter<SliderAdapters.SlideViewHolder> {
    private List<String> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;

    public SliderAdapters(Context context, List<String> sliderItems, ViewPager2 viewPager2) {
        this.context=context;
        this.sliderItems=sliderItems;
        this.viewPager2=viewPager2;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SlideViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        holder.setImageView(sliderItems.get(position));
        if (position == sliderItems.size() - 2) {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class SlideViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public SlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageSlide);
        }

        void setImageView(String imageUrl) {
            RequestOptions requestOptions=new RequestOptions();
            requestOptions=requestOptions.transform(new CenterCrop(), new RoundedCorners(60));
            //Lấy hình ảnh poster từ URL lên imageView
            Glide.with(context).load(imageUrl).apply(requestOptions).into(imageView);
        }
    }

    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };
}