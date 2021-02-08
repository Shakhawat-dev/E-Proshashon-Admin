package com.metacoders.e_proshashonadmin.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.metacoders.e_proshashonadmin.Models.ComplainModel;
import com.metacoders.e_proshashonadmin.R;


public class viewholderForAllCompainList extends RecyclerView.ViewHolder {

    private static viewholderForAllCompainList.Clicklistener mclicklistener;
    public TextView statusTv;
    View mview;

    public viewholderForAllCompainList(@NonNull View itemView) {
        super(itemView);

        mview = itemView;


        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mclicklistener.onItemClick(v, getAdapterPosition());

            }
        });


    }

    public static void setOnClickListener(Clicklistener clickListener) {


        mclicklistener = clickListener;


    }

    public void setDataToView(Context context, ComplainModel model) {

        TextView title = mview.findViewById(R.id.complain_title);
        TextView desc = mview.findViewById(R.id.complain_details);

        title.setText(model.getName());
        //    fareView.setText(fare);
        desc.setText(model.getComment());

//        Glide.with(context)
//                .load(model.getImage())
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .override(250, 250)
//              .placeholder(R.drawable.placeholder)
//                .into(image);

    }

    public interface Clicklistener {

        void onItemClick(View view, int postion);

    }

}
