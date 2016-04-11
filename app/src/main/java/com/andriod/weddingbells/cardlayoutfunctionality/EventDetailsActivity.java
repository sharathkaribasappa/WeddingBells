
package com.andriod.weddingbells.cardlayoutfunctionality;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.andriod.weddingbells.Adapters.PagerAdapter;
import com.andriod.weddingbells.common.BitmapScaler;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TabHost;

import com.andriod.weddingbells.R;

public class EventDetailsActivity extends FragmentActivity implements TabHost.OnTabChangeListener,
        ViewPager.OnPageChangeListener {

    ImageView mImageView;
    private static String LOG_TAG = "DetailsActivity";

    private TabHost mTabHost;
    private ViewPager mViewPager;
    private HashMap<String, TabInfo> mapTabInfo = new HashMap<String, TabInfo>();
    private PagerAdapter mPagerAdapter;

    /**
     * Maintains extrinsic info of a tab's construct
     */
    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //getActionBar().setDisplayHomeAsUpEnabled(true); // for back arrow
        //getActionBar().setDisplayShowHomeEnabled(true);

        mImageView = (ImageView) findViewById(R.id.ImageView_details_t);

        // Intialise ViewPager
        this.intialiseViewPager();
        this.initialiseTabHost(savedInstanceState);
        if (savedInstanceState != null) {
            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); // set the tab as per
                                                                              // the saved state
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        mImageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start a dialog for this to show 2 options:
                // Take Photo
                // Import photo from gallery
            }
        });

        try {
            mImageView.setImageBitmap(new BitmapScaler(getImage(), 300).getScaled());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String getDirectory() {
        String mImagesFolder = null;
        if (Environment.getExternalStorageState() == null) {
            mImagesFolder = Environment.getDataDirectory().getAbsolutePath()
                    + "/MyAppImagesFolder/";
        } else if (Environment.getExternalStorageState() != null) {
            mImagesFolder = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/MyAppImagesFolder/";
        }

        return mImagesFolder;
    }

    private File getImage() {
        File filePath = new File(getDirectory() + "batman.jpg");
        return filePath;
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); // save the tab selected
        super.onSaveInstanceState(outState);
    }

    /**
     * Initialise ViewPager
     */
    private void intialiseViewPager() {

        List<Fragment> fragments = new Vector<Fragment>();
        fragments.add(Fragment.instantiate(this, EventDetailsUpdatesFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, EventDetailsCommentsFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, EventDetailsUserListFragment.class.getName()));
        mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager(), fragments);
        //
        mViewPager = (ViewPager) super.findViewById(R.id.viewpager);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);
    }

    /**
     * Initialise the Tab Host
     */
    private void initialiseTabHost(Bundle args) {
        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        EventDetailsActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("Updates")
                .setIndicator("Updates"), (tabInfo = new TabInfo("Updates", EventDetailsUpdatesFragment.class,
                args)));
        mapTabInfo.put(tabInfo.tag, tabInfo);

        EventDetailsActivity.AddTab(this, this.mTabHost,
                this.mTabHost.newTabSpec("Comments").setIndicator("Comments"),
                (tabInfo = new TabInfo(
                        "Comments", EventDetailsCommentsFragment.class, args)));
        mapTabInfo.put(tabInfo.tag, tabInfo);

        EventDetailsActivity.AddTab(this, this.mTabHost,
                this.mTabHost.newTabSpec("Event Users").setIndicator("Event Users"), (tabInfo = new TabInfo(
                        "Event Users",
                        EventDetailsUserListFragment.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        // Default to first tab
        this.onTabChanged("Tab1");
        //
        mTabHost.setOnTabChangedListener(this);
    }

    /**
     * Add Tab content to the Tabhost
     * 
     * @param activity
     * @param tabHost
     * @param tabSpec
     */
    private static void AddTab(EventDetailsActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec,
            TabInfo tabInfo) {
        tabSpec.setContent(new TabFactory(activity));
        tabHost.addTab(tabSpec);
    }

    /**
     * (non-Javadoc)
     * 
     * @see TabHost.OnTabChangeListener#onTabChanged(String)
     */
    public void onTabChanged(String tag) {
        // TabInfo newTab = this.mapTabInfo.get(tag);
        int pos = this.mTabHost.getCurrentTab();
        Log.d(LOG_TAG, "Position:" + pos + mViewPager);
        mViewPager.setCurrentItem(pos);
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
     */
    @Override
    public void onPageScrolled(int position, float positionOffset,
            int positionOffsetPixels) {

    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected(int)
     */
    @Override
    public void onPageSelected(int position) {
        this.mTabHost.setCurrentTab(position);
    }

    /*
     * (non-Javadoc)
     * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrollStateChanged(int)
     */
    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
