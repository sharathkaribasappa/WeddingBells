package com.andriod.weddingbells.calendarDialog;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andriod.weddingbells.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class CalenderGridAdapter extends BaseAdapter implements View.OnClickListener {
    private static final String TAG = "CalenderGridAdapter";
    private Context mContext;

    private List<String> mListData;
    private int mCurrentMonth;
    private static final int DAY_OFFSET = 1;
    private int mCurrentDayOfMonth;
    private int mCurrentWeekDay;
    private TextView gridcell;
    private ImageView num_events_per_day;
    private HashMap<String, Integer> eventsPerMonthMap;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "dd-MMM-yyyy");

    // Days in Current Month
    public CalenderGridAdapter(Context context, int textViewResourceId,
                               int month, int year) {
        super();
        mContext = context;
        mListData = new ArrayList<String>();
        Calendar calendar = Calendar.getInstance();
        mCurrentDayOfMonth = (calendar.get(Calendar.DAY_OF_MONTH));
        mCurrentWeekDay = calendar.get(Calendar.DAY_OF_WEEK);
        mCurrentMonth = calendar.get(Calendar.MONTH);

        printMonth(month, year);

// Find Number of Events
        eventsPerMonthMap = findNumberOfEventsPerMonth(year, month);
    }

    public String getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    /**
     * Prints Month
     *
     * @param mm
     * @param yy
     */
    private void printMonth(int mm, int yy) {
        int trailingSpaces = 0;
        int daysInPrevMonth = 0;
        int prevMonth = 0;
        int prevYear = 0;
        int nextMonth = 0;
        int nextYear = 0;

        int month = mm - 1;
        String currentMonthName = getMonthAsString(month);
        int daysInMonth = getNumberOfDaysOfMonth(month);


        GregorianCalendar cal = new GregorianCalendar(yy, month, 1);

        if (month == 11) {
            prevMonth = month - 1;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 0;
            prevYear = yy;
            nextYear = yy + 1;
        } else if (month == 0) {
            prevMonth = 11;
            prevYear = yy - 1;
            nextYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
            nextMonth = 1;
        } else {
            prevMonth = month - 1;
            nextMonth = month + 1;
            nextYear = yy;
            prevYear = yy;
            daysInPrevMonth = getNumberOfDaysOfMonth(prevMonth);
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;


        if (cal.isLeapYear(cal.get(Calendar.YEAR)))
            if (mm == 2)
                ++daysInMonth;
            else if (mm == 3)
                ++daysInPrevMonth;

// Trailing Month days
        for (int i = 0; i < trailingSpaces; i++) {
            mListData.add(String
                    .valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET)
                            + i)
                    + "-WHITE"
                    + "-"
                    + getMonthAsString(prevMonth)
                    + "-"
                    + prevYear);
        }

// Current Month Days
        for (int i = 1; i <= daysInMonth; i++) {
            if (i == mCurrentDayOfMonth && month == mCurrentMonth) {
                mListData.add(String.valueOf(i) + "-BLUE" + "-"
                        + getMonthAsString(month) + "-" + yy);
            } else {
                mListData.add(String.valueOf(i) + "-GREY" + "-"
                        + getMonthAsString(month) + "-" + yy);
            }
        }

// Leading Month days
        for (int i = 0; i < mListData.size() % 7; i++) {
            mListData.add(String.valueOf(i + 1) + "-WHITE" + "-"
                    + getMonthAsString(nextMonth) + "-" + nextYear);
        }
    }

    /**
     * NOTE: YOU NEED TO IMPLEMENT THIS PART Given the YEAR, MONTH, retrieve
     * ALL entries from a SQLite database for that month. Iterate over the
     * List of All entries, and get the dateCreated, which is converted into
     * day.
     *
     * @param year
     * @param month
     * @return
     */
    private HashMap<String, Integer> findNumberOfEventsPerMonth(int year,
                                                                int month) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("28",5);
        map.put("29",1);
        return map;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.screen_grid_cell, parent, false);
        }

        gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
        gridcell.setOnClickListener(this);

// ACCOUNT FOR SPACING

        String[] day_color = mListData.get(position).split("-");
        String day = day_color[0];
        String month = day_color[2];
        String year = day_color[3];
        if ((!eventsPerMonthMap.isEmpty()) && (eventsPerMonthMap != null)) {
            if (!day_color[1].equals("WHITE")&&eventsPerMonthMap.containsKey(day)) {
                num_events_per_day = (ImageView) row
                        .findViewById(R.id.num_events_per_day);
//                Integer numEvents = (Integer) eventsPerMonthMap.get(day);
//                num_events_per_day.setText(numEvents.toString());
                num_events_per_day.setVisibility(View.VISIBLE);
            }
        }

// Set the Day GridCell
        gridcell.setText(day);
        gridcell.setTag(day + "-" + month + "-" + year);

        if (day_color[1].equals("WHITE")) {
            gridcell.setTextColor(Color.WHITE);
        }
        if (day_color[1].equals("GREY")) {
            gridcell.setTextColor(Color.DKGRAY);
        }
        if (day_color[1].equals("BLUE")) {
            gridcell.setTextColor(Color.BLUE);
        }
        return row;
    }

    @Override
    public void onClick(View view) {
        String date_month_year = (String) view.getTag();
//        selectedDayMonthYearButton.setText("Selected: " + date_month_year);
        try {
            Date parsedDate = dateFormatter.parse(date_month_year);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getMonthAsString(int i) {
        String[] months = {"January", "February", "March",
                "April", "May", "June", "July", "August", "September",
                "October", "November", "December"};
        return months[i];
    }

    private String getWeekDayAsString(int i) {
        String[] weekdays = new String[]{
                "Sun", "Mon", "Tue",
                "Wed", "Thu", "Fri", "Sat"
        };
        return weekdays[i];
    }

    private int getNumberOfDaysOfMonth(int i) {
        int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30,
                31, 30, 31};
        return daysOfMonth[i];
    }


}


