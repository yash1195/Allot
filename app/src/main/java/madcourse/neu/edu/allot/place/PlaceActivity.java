package madcourse.neu.edu.allot.place;

import android.content.Intent;
import android.os.Binder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.models.Group;
import madcourse.neu.edu.allot.task.AddTaskActivity;

public class PlaceActivity extends AppCompatActivity {

    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private String place;
    private Group groupData;

    // group data
    static Bundle groupDataBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        Bundle passedData = getIntent().getExtras();

        if(passedData.get("groupData") != null) {

            // passed data
            place = getIntent().getStringExtra("place");


            groupData = (Group) passedData.get("groupData");
            groupDataBundle = new Bundle();
            groupDataBundle.putSerializable("groupData", groupData);
        }

        toolbar.setTitle(place);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);

                // place info
                intent.putExtra("place", place);

                // group data to be passed
                intent.putExtra("groupData", groupData);
                Log.d("PlaceActivity", groupData.toString());
                startActivity(intent);
            }
        });

    }

    /**
     * Sets up the viewpager.
     *
     * @param pager view pager
     */
    private void setupViewPager(ViewPager pager) {

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        Fragment dashBoardFragment = new DashboardFragment();
        dashBoardFragment.setArguments(groupDataBundle);

        Fragment tasksSectionFragment = new TaskFragment();
        tasksSectionFragment.setArguments(groupDataBundle);

        adapter.addFragment(dashBoardFragment, "Dashboard");
        adapter.addFragment(tasksSectionFragment, "Tasks");
        viewPager.setAdapter(adapter);
    }

    /**
     * Inner class needed for creating custom adapater.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}
