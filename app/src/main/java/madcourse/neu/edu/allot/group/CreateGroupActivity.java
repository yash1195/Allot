package madcourse.neu.edu.allot.group;

import android.content.DialogInterface;
import android.content.Intent;
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

public class CreateGroupActivity extends AppCompatActivity {

    Button createGroupButton;
    EditText createGroupEditText;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    TextView userNameMenu;
    TextView settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        final int groupCode = 1234;

        createGroupButton = (Button) findViewById(R.id.createGroupButton);
        createGroupEditText = (EditText) findViewById(R.id.creatGroupEdittext);

        createGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (createGroupEditText.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Enter Valid Group Name", Toast.LENGTH_SHORT).show();
                } else {
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
            }
        });
    }


}
