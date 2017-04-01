package com.example.chie.bulletinboard_0_5_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

/**
 * Created by chie on 2017/03/29.
 */

public class BoardAdapter extends RealmBaseAdapter<Board> implements ListAdapter{

    private Context context;
    private DeleteListener deleteListener;

    public static class ViewHolder {
        TextView textViewPost;
        ImageView imageViewCan;
    }

    public void setCallback(DeleteListener callback){
        deleteListener = callback;
    }
     public interface DeleteListener {
         void delete(long postId);
     }

     public BoardAdapter(Context context, OrderedRealmCollection<Board> boards){
         super(context, boards);
         this.context = context;
     }

     @Override
    public long getItemId(int position){
         //ListView内の一つのアイテムに対し
         //
         return getItem(position).getId();
     }

     @Override
    public View getView(final int position, View convertView, ViewGroup parent){

         ViewHolder viewHolder;
         if(convertView == null){
             convertView = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false);

             viewHolder = new ViewHolder();
             viewHolder.textViewPost = (TextView) convertView.findViewById(R.id.text_view_post);

             ImageView binImage = (ImageView) convertView.findViewById(R.id.image_bin);

             binImage.setTag(getItemId(position));
             binImage.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                 }
             });
             binImage.setOnLongClickListener(new View.OnLongClickListener(){
                 @Override
                 public boolean onLongClick(View v){
                     deleteListener.delete((long) v.getTag());
                     return true;
                 }
             });

             viewHolder.imageViewCan = binImage;
             convertView.setTag(viewHolder);

         } else {
             viewHolder = (ViewHolder) convertView.getTag();
             viewHolder.imageViewCan.setTag(getItemId(position));
         }

         viewHolder.textViewPost.setText(getItem(position).getPost());
         return convertView;
     }
}

