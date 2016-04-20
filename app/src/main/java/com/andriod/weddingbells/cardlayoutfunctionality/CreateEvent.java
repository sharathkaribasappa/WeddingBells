package com.andriod.weddingbells.cardlayoutfunctionality;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andriod.weddingbells.R;
import com.andriod.weddingbells.common.DatePickerFragment;
import com.andriod.weddingbells.common.ImagePicker;
import com.andriod.weddingbells.common.TimePickerFragment;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class CreateEvent extends AppCompatActivity implements DatePickerFragment.DatePickerInterface,TimePickerFragment.TimePickerInterface,View.OnClickListener{
    private CollapsingToolbarLayout mCollapsingToolbar;
    private TextView mDateTextView;
    private TextView mTimeTextView;
    private FloatingActionButton mEditImageButton;
    private ImageView mEventImage;
    private static final int PICK_IMAGE_ID = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_create_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbar.setTitle("Create Event");
        dynamicToolbarColor();

        mDateTextView = (TextView)findViewById(R.id.createEventShowSelectedDate);
        mTimeTextView = (TextView)findViewById(R.id.createEventShowSelectedTime);
        mEditImageButton = (FloatingActionButton)findViewById(R.id.createEventChangeEventPic);
        mEditImageButton.setOnClickListener(this);
        mEventImage = (ImageView)findViewById(R.id.CreateEventEventImage);
    }

    private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.bat);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {

            @Override
            public void onGenerated(Palette palette) {
                mCollapsingToolbar.setContentScrimColor(palette.getMutedColor(getResources().getColor(R.color.colorPrimary)));
                mCollapsingToolbar.setStatusBarScrimColor(palette.getMutedColor(getResources().getColor(R.color.colorPrimaryDark)));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    public void ShowDatePicker(View v){
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void ShowTimePicker(View v){
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void onDateSelectedSuccessfully(int year, int month, int day){
        String DATE_FORMAT = "MMM dd, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        mDateTextView.setText("" + dayLongName + ", " + sdf.format(calendar.getTime()));
    }

    @Override
    public void onTimeSetSuccessfully(int hourOfDay, int minute) {
        TimeZone timezone = TimeZone.getDefault();
        mTimeTextView.setText(""+hourOfDay+" : " + minute + " , " + timezone.getDisplayName(false, TimeZone.SHORT));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createEventChangeEventPic:
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                mEventImage.setImageBitmap(bitmap);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}




