package com.andriod.weddingbells.calendarDialog;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.andriod.weddingbells.R;

import java.util.Calendar;
import java.util.Locale;

public class CalendarDialogFragment extends DialogFragment implements View.OnClickListener {

    private final String TAG = "CalendarDialogFragment";

    private TextView mMonthTextView;
    private ImageView mPrevMonthImageView;
    private ImageView mNextMonthImageView;
    private GridView mCalenderGridView;
    private CalenderGridAdapter mAdapter;
    private Calendar mCalendar;
    private int mMonth, mYear;
    private final DateFormat dateFormatter = new DateFormat();
    private static final String dateTemplate = "MMMM yyyy";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.calendar_dialog_layout, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCalendar = Calendar.getInstance(Locale.getDefault());
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mYear = mCalendar.get(Calendar.YEAR);

        mPrevMonthImageView = (ImageView) view.findViewById(R.id.prevMonth);
        mPrevMonthImageView.setOnClickListener(this);

        mMonthTextView = (TextView) view.findViewById(R.id.currentMonth);
        mMonthTextView.setText(DateFormat.format(dateTemplate,
                mCalendar.getTime()));

        mNextMonthImageView = (ImageView) view.findViewById(R.id.nextMonth);
        mNextMonthImageView.setOnClickListener(this);

        mCalenderGridView = (GridView) view.findViewById(R.id.calendar);

        mAdapter = new CalenderGridAdapter(getActivity(),
                R.id.calendar_day_gridcell, mMonth, mYear);
        mAdapter.notifyDataSetChanged();
        mCalenderGridView.setAdapter(mAdapter);
    }

    private void setGridCellAdapterToDate(int month, int year) {
        mAdapter = new CalenderGridAdapter(getActivity(),
                R.id.calendar_day_gridcell, month, year);
        mCalendar.set(year, month - 1, mCalendar.get(Calendar.DAY_OF_MONTH));
        mMonthTextView.setText(DateFormat.format(dateTemplate,
                mCalendar.getTime()));
        mAdapter.notifyDataSetChanged();
        mCalenderGridView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v == mPrevMonthImageView) {
            if (mMonth <= 1) {
                mMonth = 12;
                mYear--;
            } else {
                mMonth--;
            }
            setGridCellAdapterToDate(mMonth, mYear);
        }
        if (v == mNextMonthImageView) {
            if (mMonth > 11) {
                mMonth = 1;
                mYear++;
            } else {
                mMonth++;
            }
            setGridCellAdapterToDate(mMonth, mYear);
        }

    }
}
