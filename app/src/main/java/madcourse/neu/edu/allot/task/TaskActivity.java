package madcourse.neu.edu.allot.task;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import madcourse.neu.edu.allot.R;

public class TaskActivity extends AppCompatActivity {

    LinearLayout taskLinerLayout;
    ArrayList<String> participants = new ArrayList<>();
    Button NudgeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        NudgeButton =(Button) findViewById(R.id.NudgeButton);

        participants.add("Prachi");
        participants.add("Yash");
        participants.add("Nay");


        LinearLayout.LayoutParams paramsTextLable = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTextLable.setMargins(5, 5, 10, 20);

        taskLinerLayout = (LinearLayout)findViewById(R.id.taskLinearLayout);
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


        for(int i=0;i<participants.size();i++){
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
                startActivity(new Intent(TaskActivity.this,PopUpActivity.class));
            }
        });
    }
}
