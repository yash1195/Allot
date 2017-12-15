package madcourse.neu.edu.allot.task;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.handlers.MarkTaskAsDoneHandler;
import madcourse.neu.edu.allot.blackbox.models.Task;
import madcourse.neu.edu.allot.blackbox.models.User;

public class TaskActivity extends AppCompatActivity {

    LinearLayout taskLinerLayout;
    ArrayList<String> participants = new ArrayList<>();
    Button NudgeButton;
    Button markAsDone;

    // task data
    Task taskData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        taskData = (Task) getIntent().getExtras().get("taskData");

        NudgeButton = (Button) findViewById(R.id.NudgeButton);
        markAsDone = (Button) findViewById(R.id.MarkButton);

        for (User participant : taskData.getParticipants()) {
            participants.add(participant.getFirstName() + " " + participant.getLastName());
        }


        LinearLayout.LayoutParams paramsTextLable = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextLable.setMargins(5, 5, 10, 20);

        taskLinerLayout = (LinearLayout) findViewById(R.id.taskLinearLayout);
        TextView allotedText = new TextView(this);
        allotedText.setText("ALLOTED TO:");
        allotedText.setTextSize(30);
        allotedText.setLayoutParams(paramsTextLable);
        taskLinerLayout.addView(allotedText);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 5, 10, 5);


        for (int i = 0; i < participants.size(); i++) {
            TextView textview = new TextView(this);
            textview.setText(participants.get(i));
            textview.setBackgroundColor(Color.WHITE);
            textview.setGravity(Gravity.CENTER | Gravity.BOTTOM);
            textview.setTextSize(20);
            textview.setLayoutParams(params);
            taskLinerLayout.addView(textview);
        }

        NudgeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nudgePopup = new Intent(TaskActivity.this, PopUpActivity.class).putExtra("taskData", taskData);
                startActivity(nudgePopup);
//                startActivity(new Intent(TaskActivity.this,PopUpActivity.class));
//                startActivity(new Intent(TaskActivity.this, PopUpActivity.class));
            }
        });

        markAsDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // requestor id-token
                SharedPreferences sharedPref = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE);
                String requestorId = sharedPref.getString(User.SHARED_PREF_TAG_ID, "NA");
                String requestorToken = sharedPref.getString(User.SHARED_PREF_TAG_TOKEN, "NA");

                MarkTaskAsDoneHandler.markAsDone(requestorId, requestorToken, taskData.getId());
                TaskActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
