package madcourse.neu.edu.allot.task;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.github.tbouron.shakedetector.library.ShakeDetector;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.handlers.NudgeHandler;
import madcourse.neu.edu.allot.blackbox.models.Task;
import madcourse.neu.edu.allot.blackbox.models.User;

public class PopUpActivity extends AppCompatActivity {

    Task taskData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int hight = dm.heightPixels;

        getWindow().setLayout((int)(width*.7),(int)(hight*.6));

        // task Data
        taskData = (Task) getIntent().getExtras().get("taskData");

        ShakeDetector.create(this, new ShakeDetector.OnShakeListener() {
            @Override
            public void OnShake() {

                Toast.makeText(getApplicationContext(), "Good Nudge!", Toast.LENGTH_SHORT).show();

                // Make Nudge Request
                String taskIdToBeNudged = taskData.getId();
                // User credentials.
                SharedPreferences sharedPref = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE);
                String userId = sharedPref.getString(User.SHARED_PREF_TAG_ID, "NA");
                String userToken = sharedPref.getString(User.SHARED_PREF_TAG_TOKEN, "NA");
                NudgeHandler.doNudge(userId, userToken,taskIdToBeNudged);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        ShakeDetector.stop();
        ShakeDetector.destroy();
        super.onDestroy();
    }
}
