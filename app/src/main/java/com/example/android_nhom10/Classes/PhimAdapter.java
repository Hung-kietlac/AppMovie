package com.example.android_nhom10.Classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android_nhom10.Activities.Xemchitiet;
import com.example.android_nhom10.R;

import java.util.List;

public class PhimAdapter extends RecyclerView.Adapter<PhimAdapter.PhimViewHolder> {
    public Context context;
    List<Phim> phimList;

    public PhimAdapter(Context context, List<Phim> phimList) {
        this.context=context;
        this.phimList=phimList;
    }

    @NonNull
    @Override
    public PhimAdapter.PhimViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.phim_item, parent, false);
        return new PhimViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhimAdapter.PhimViewHolder holder, int position) {
        Phim phim=phimList.get(position);
        holder.bind(phim);
        //Tải ảnh và hiển thị ảnh từ URL lên imageView
        Glide.with(context).load(phim.getPoster()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lưu dữ liệu intent khi nhấn vào poster sang Activity Xemchitiet
                Intent intent=new Intent(context, Xemchitiet.class);
                intent.putExtra("TEN_PHIM", phim.getTenphim());
                intent.putExtra("POSTER", phim.getPoster());
                intent.putExtra("THE_LOAI", phim.getTheloai());
                intent.putExtra("NGAY_CHIEU", phim.getNgaychieu());
                intent.putExtra("THOI_LUONG", phim.getThoiluong());
                intent.putExtra("TEN_DAO_DIEN", phim.getTendaodien());
                intent.putExtra("DIEN_VIEN", phim.getDienvien());
                intent.putExtra("NOI_DUNG", phim.getNoidung());
                intent.putExtra("QUOC_GIA", phim.getQuocgia());
                intent.putExtra("GIA_VE", phim.getGiave());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return phimList.size();
    }

    public class PhimViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public PhimViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            textView=itemView.findViewById(R.id.textView);
        }

        public void bind(Phim phim) {
            textView.setText(phim.getTenphim());
        }
    }
}