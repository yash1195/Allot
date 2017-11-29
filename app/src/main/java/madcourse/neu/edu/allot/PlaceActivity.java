package madcourse.neu.edu.allot;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PlaceActivity extends AppCompatActivity {

    private FragmentTransaction fragmentTransaction;
    private TabItem dashboard;
    private TabItem tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        dashboard = findViewById(R.id.tab_dashboard);
        tasks = findViewById(R.id.tab_tasks);

    }

    /**
     * Opens the dashboard fragment.
     */
    public void openDashboard() {
        fragmentTransaction = getFragmentManager().beginTransaction();
        DashboardFragment dashboardFragment = new DashboardFragment();
        fragmentTransaction.replace(android.R.id.content, dashboardFragment);
        fragmentTransaction.commit();
    }
}
