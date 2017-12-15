package madcourse.neu.edu.allot.participant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import madcourse.neu.edu.allot.R;

public class ParticipantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("name"));
    }
    private static final int BACK_BUTTON = 16908332;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == BACK_BUTTON) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
