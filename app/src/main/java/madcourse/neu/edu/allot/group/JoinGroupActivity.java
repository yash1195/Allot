package madcourse.neu.edu.allot.group;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.handlers.JoinGroupHandler;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.blackbox.responders.JoinGroupResponder;

public class JoinGroupActivity extends AppCompatActivity implements JoinGroupResponder {

    Button joinGroupButton;
    EditText joinGroupEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_group);

        joinGroupButton = (Button) findViewById(R.id.joinGroupButton);
        joinGroupEditText = (EditText) findViewById(R.id.joinGroupEdittext);

        /**
         * User credentials.
         */
        SharedPreferences sharedPref = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE);

        final String userId = sharedPref.getString(User.SHARED_PREF_TAG_ID, "NA");
        final String userToken = sharedPref.getString(User.SHARED_PREF_TAG_TOKEN, "NA");

        joinGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // group code
                String groupCode = joinGroupEditText.getText().toString();

                if (groupCode.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Group Name", Toast.LENGTH_SHORT).show();
                }
                else{

                    JoinGroupHandler.doJoin(JoinGroupActivity.this, userId, userToken, groupCode);
                }
            }
        });
    }

    @Override
    public void onSuccessfulJoinGroup() {

        Intent GroupActivity = new Intent(JoinGroupActivity.this, GroupActivity.class);
        startActivity(GroupActivity);
    }

    @Override
    public void onFailedJoinGroup(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
