package madcourse.neu.edu.allot.group;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.handlers.CreateGroupHandler;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.blackbox.responders.CreateGroupResponder;

public class CreateGroupActivity extends AppCompatActivity implements CreateGroupResponder {

    Button createGroupButton;
    EditText createGroupEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);


        createGroupButton = (Button) findViewById(R.id.createGroupButton);
        createGroupEditText = (EditText) findViewById(R.id.creatGroupEdittext);

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String groupName = createGroupEditText.getText().toString();

                if (groupName.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Group Name", Toast.LENGTH_SHORT).show();
                } else {

                    // Make request

                    /**
                     * User credentials.
                     */
                    SharedPreferences sharedPref = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE);

                    String userId = sharedPref.getString(User.SHARED_PREF_TAG_ID, "NA");
                    String userToken = sharedPref.getString(User.SHARED_PREF_TAG_TOKEN, "NA");

                    CreateGroupHandler.doCreate(CreateGroupActivity.this , userId, userToken, groupName);
                }
            }
        });
    }


    @Override
    public void onSuccessfulCreateGroup(String groupCode) {

        AlertDialog.Builder createGroupAlert = new AlertDialog.Builder(CreateGroupActivity.this);
        createGroupAlert.setMessage("Group Code is : " + groupCode)
                .setCancelable(false)
                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent GroupActivity = new Intent(CreateGroupActivity.this, GroupActivity.class);
                        startActivity(GroupActivity);
                    }
                });
        createGroupAlert.show();
    }

    @Override
    public void onFailedCreateGroup(String msg) {

        AlertDialog.Builder createGroupAlert = new AlertDialog.Builder(CreateGroupActivity.this);
        createGroupAlert
                .setMessage("Unable to Create Group: " + msg)
                .setCancelable(false)
                .setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent GroupActivity = new Intent(CreateGroupActivity.this, CreateGroupActivity.class);
                        startActivity(GroupActivity);
                    }
                });
        createGroupAlert.show();
    }
}
