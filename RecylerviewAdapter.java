package net.skillbooster.projectone;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static net.skillbooster.projectone.MainActivity.Extra_url;

public class RecylerviewAdapter  extends  RecyclerView.Adapter<RecylerviewAdapter.RecylerViewHolder>{
    private Context context;
    private ArrayList<PerItemVariable> arrayList;
    private OnItemClickListener mListener;
    private Intent myintent;


    public interface  OnItemClickListener{

        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener){

        mListener = listener;
    }



    public RecylerviewAdapter() {
    }

    public RecylerviewAdapter(Context context, ArrayList<PerItemVariable> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecylerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.per_video_row,viewGroup,false);

        return new RecylerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerViewHolder recylerViewHolder, int i) {
        final PerItemVariable currentitemRow = arrayList.get(i);

        String imgurl = currentitemRow.getImageurl();
        String title  = currentitemRow.getHeading();
        final String url = currentitemRow.getVideourl();

        recylerViewHolder.textView.setText(title);
        Picasso.get().load(imgurl).fit().centerInside().into(recylerViewHolder.imageView);
        recylerViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myintent = new Intent(context,ViewVideo.class);
                myintent.putExtra(Extra_url,url);
                context.startActivity(myintent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public  class RecylerViewHolder extends RecyclerView.ViewHolder {

            private  ImageView imageView;
            private TextView textView;


        public RecylerViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.per_video_row_img);
            textView = itemView.findViewById(R.id.per_video_row_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null){

                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){

                            mListener.onItemClick(position);

                        }
                    }
                }
            });
        }
    }
}
