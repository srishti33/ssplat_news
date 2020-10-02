package com.example.kinnect;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;
import com.example.kinnect.models.Article;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.example.kinnect.models.Article;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private List<Article> article;
    private Context context;

    public Adapter(List<Article> articles, Context context) {
        this.article = articles;
        this.context = context;
    }

    private OnItemClickListener onItemClickListener;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
final MyViewHolder holder=holders;
Article model=article.get(position);
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();
        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>(){
            @Override
                    public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource){
                return false;
            }
            @Override
                    public boolean onResourceReady(Drawable resource,Object model,Target<Drawable> target, DataSource datasource,boolean isFirstResource){
                return false;
            }

        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title,desc,author,publish,source,time;
        ImageView img;
        ProgressBar progress;
        OnItemClickListener onItemClickListener;
        public MyViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            author=itemView.findViewById(R.id.author);
            publish=itemView.findViewById(R.id.publish);
            source=itemView.findViewById(R.id.source);
            time=itemView.findViewById(R.id.time);
            img=itemView.findViewById(R.id.img);
            progress=itemView.findViewById(R.id.load);
            this.onItemClickListener=onItemClickListener;
        }

        @Override
        public void onClick(View v){
onItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }

}
