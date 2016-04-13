package com.andriod.weddingbells.cardlayoutfunctionality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andriod.weddingbells.R;


/**
 * Created by inksriniva on 4/6/2016.
 */
public class EventBasicDetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.event_basic_details, container, false);
        return rootview;
    }
}
