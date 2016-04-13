
package com.andriod.weddingbells.notUsed;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.andriod.weddingbells.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewAdapter_EventsList_Old extends RecyclerView
        .Adapter<RecyclerViewAdapter_EventsList_Old
        .MainViewHolder> {
    private final String LOG_TAG = "RecyclerViewAdapter_EventsList_Old";
    private ArrayList<EventListDataObject_old> mDataset;
    private static MyClickListener myClickListener;
    static Context mCtx;
    private static final int TYPE_HEADING = 0;
    private static final int TYPE_CARD = 1;

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        public MainViewHolder(View itemview) {
            super(itemview);
        }

    }
    
    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent,
            int viewType) {
        switch (viewType) {
            case TYPE_HEADING:
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.date_row_header, parent, false);
                mCtx = parent.getContext();

                TitleObjectHolder titleObjectHolder = new TitleObjectHolder(view);
                return titleObjectHolder;
            case TYPE_CARD:
                View view1 = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.events_list_card_view_row, parent, false);
                mCtx = parent.getContext();

                DataObjectHolder dataObjectHolder = new DataObjectHolder(view1);
                return dataObjectHolder;
            default:
                break;
        }
        return null;

    }

    public static class DataObjectHolder extends MainViewHolder
            implements View
            .OnClickListener {
        TextView heading;
        TextView desc;
        ImageView mImageView;
        Toolbar mToolBar;

        TextView date;

        public DataObjectHolder(View itemView) {
            super(itemView);

            this.heading = (TextView) itemView.findViewById(R.id.textView);
            desc = (TextView) itemView.findViewById(R.id.textView2);
            mImageView = (ImageView) itemView.findViewById(R.id.ImageView);
            mToolBar = (Toolbar) itemView.findViewById(R.id.card_view_toolbar);
            mToolBar.inflateMenu(R.menu.main);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }
    
    public static class TitleObjectHolder extends MainViewHolder {
        TextView date;

        public TitleObjectHolder(View itemView) {
            super(itemView);

            itemView.setEnabled(false);
            date = (TextView) itemView.findViewById(R.id.date_header_listview);
        }

    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RecyclerViewAdapter_EventsList_Old(ArrayList<EventListDataObject_old> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 4 == 0) {
            return TYPE_HEADING;
        } else {
            return TYPE_CARD;
        }
    }

   

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        if (position % 4 == 0) {
            System.out.println("Set date");
            TitleObjectHolder mHolder = (TitleObjectHolder) holder;
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());
            mHolder.date.setText(formattedDate);
        } else {
            DataObjectHolder mHolder = (DataObjectHolder) holder;
            mHolder.heading.setText(mDataset.get(position).getmText1());
            mHolder.desc.setText(mDataset.get(position).getmText2());
            mHolder.mToolBar.setTitle(mDataset.get(position).getmTitle());
            mHolder.mImageView.setImageDrawable(Drawable.createFromPath(mDataset.get(position)
                    .getImage().toString()));
        }
    }

    public void addItem(EventListDataObject_old dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
