package madcourse.neu.edu.allot.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.place.PlaceActivity;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText description;
    private EditText location;
    private Button buttonParticipant;
    private Button buttonLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("place"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        taskName = (EditText) findViewById(R.id.editText_taskname);
        description = (EditText) findViewById(R.id.editText_description);
        buttonParticipant = (Button) findViewById(R.id.button_allot);
        buttonLocation = (Button) findViewById(R.id.button_choose_location);
        location = (EditText) findViewById(R.id.text_location);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String nameOfLocation = data.getStringExtra("placename");
                location.setText(nameOfLocation);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    /**
     * On Choose Location button click.
     *
     * @param view view
     */
    public void chooseLocation(View view) {
        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivityForResult(intent, 1);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent = new Intent(getApplicationContext(), PlaceActivity.class);
        startActivityForResult(myIntent, 1);
        return true;

    }
}
