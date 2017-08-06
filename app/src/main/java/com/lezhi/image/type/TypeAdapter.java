package com.lezhi.image.type;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lezhi.image.ImageActivity;
import com.lezhi.image.R;
import com.lezhi.image.api.model.Pin;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lezhi on 2017/2/26.
 */

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pin> mPinsList;

    public TypeAdapter(Context context) {
        mContext = context;
        mPinsList = new ArrayList<Pin>();
    }

    public void addListNotify(List<Pin> result) {
        mPinsList.addAll(result);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(itemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Pin.ImageFile imageFile = mPinsList.get(position).getFile();
        if (imageFile != null) {
            Picasso.with(mContext)
                    .load(imageFile.getImageUrl())
                    .placeholder(R.color.colorAccent)
                    .into(holder.imageView);
        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageActivity.startImageActivity(mContext, mPinsList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPinsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_view)
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
