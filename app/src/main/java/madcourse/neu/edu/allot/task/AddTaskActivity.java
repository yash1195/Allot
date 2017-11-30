package madcourse.neu.edu.allot.task;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import madcourse.neu.edu.allot.R;

public class AddTaskActivity extends AppCompatActivity {

    private EditText taskName;
    private EditText description;
    private Button buttonParticipant;
    private Button buttonLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        taskName = (EditText) findViewById(R.id.editText_taskname);
        description = (EditText) findViewById(R.id.editText_description);
        buttonParticipant = (Button) findViewById(R.id.button_allot);
        buttonLocation = (Button) findViewById(R.id.button_choose_location);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    /**
     * On Choose Location button click.
     *
     * @param view view
     */
    public void chooseLocation(View view) {
        // TODO: open map api
    }
}
