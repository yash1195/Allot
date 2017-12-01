package madcourse.neu.edu.allot.group;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.place.CardAdapter;
import madcourse.neu.edu.allot.place.PlaceActivity;

public class GroupActivity extends AppCompatActivity {

    private ListView groupList;
    private ArrayList<String> list;
    private CardAdapter cardAdapter;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    TextView userNameMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        userNameMenu = (TextView) findViewById(R.id.userNameMenu);
        userNameMenu.setText("Prachi Sharma");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       ArrayList<String> testingGroupButtons = new ArrayList();

        testingGroupButtons.add("Appartment");
        testingGroupButtons.add("Office");

        cardAdapter = new CardAdapter(testingGroupButtons, getApplicationContext(),
               R.layout.card_group, PlaceActivity.class);
        groupList = (ListView) findViewById(R.id.list_groups);
        groupList.setAdapter(cardAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
