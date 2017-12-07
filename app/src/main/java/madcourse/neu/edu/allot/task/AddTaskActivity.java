package madcourse.neu.edu.allot.task;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import madcourse.neu.edu.allot.BuildConfig;
import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.place.PlaceActivity;

public class AddTaskActivity extends AppCompatActivity implements OnCompleteListener<Void> {

    private EditText taskName;
    private EditText description;
    private EditText location;
    private Button buttonParticipant;
    private Button buttonLocation;
    private ListView allotList;
    private List<CheckboxModel> users;
    private CheckboxAdapter checkboxAdapter;

    private LatLng selectedLatLng;
    private String nameOfLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        selectedLatLng = null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("place"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        geofenceList = new ArrayList<>();

        taskName = (EditText) findViewById(R.id.editText_taskname);
        description = (EditText) findViewById(R.id.editText_description);
        buttonLocation = (Button) findViewById(R.id.button_choose_location);
        location = (EditText) findViewById(R.id.text_location);
        allotList = (ListView) findViewById(R.id.list_allot_participant);

        // This is where you pass the list of participants in the group
        // I am passing in an object called CheckboxModel which contains a User instance and a boolean
        // this way, we can know which particpants were selected when the task is created.
        users = new ArrayList<>();
        User user = new User();
        user.setFirstName("Nay");
        users.add(new CheckboxModel(user));
        User user1 = new User();
        user1.setFirstName("Yash");
        users.add(new CheckboxModel(user1));
        User user3 = new User();
        user3.setFirstName("Prachi");
        users.add(new CheckboxModel(user3));
        checkboxAdapter = new CheckboxAdapter(users, this, R.layout.card_checkbox_participant);
        allotList.setAdapter(checkboxAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle bundle = data.getExtras();
                nameOfLocation = bundle.get("placename").toString();
                selectedLatLng = (LatLng) bundle.get("latlng");
                location.setText(nameOfLocation);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_task, menu);
        return true;
    }

    private static final String TAG = "ADDTASK:";
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 99;
    private static final float GEOFENCE_RADIUS_IN_METERS = 1609;
    private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * 60 * 60 * 1000;
    private static final String GEOFENCES_ADDED_KEY = "com.google.android.gms.location.Geofence";
    private PendingIntent mGeofencePendingIntent;
    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList;


    @SuppressWarnings("MissingPermission")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            if (!checkPermissions()) {
                requestPermissions();
                return false;
            }
            if (taskName.getText().toString().trim().isEmpty()) {
                // TODO: show dialog that says TaskName required.
                return false;
            }
            if (selectedLatLng != null) {
                geofenceList.add(new Geofence.Builder()
                        .setRequestId(nameOfLocation)
                        .setCircularRegion(
                                selectedLatLng.latitude,
                                selectedLatLng.longitude,
                                GEOFENCE_RADIUS_IN_METERS
                        )
                        .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                                Geofence.GEOFENCE_TRANSITION_EXIT |
                                Geofence.GEOFENCE_TRANSITION_DWELL)
                        .setLoiteringDelay(15000)
                        .build());
                geofencingClient = LocationServices.getGeofencingClient(this);

                geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                        .addOnCompleteListener(this);
            }

            // TODO: create a task object and store

            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates a task object with user inputs.
     */
    public void createTask() {
        for (CheckboxModel checkbox : users) {
            if (checkbox.checked) {
                // TODO: include this user as a participant for the task
                // Access user with checkbox.user
            }
        }
    }

    public GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER |
                GeofencingRequest.INITIAL_TRIGGER_EXIT |
                GeofencingRequest.INITIAL_TRIGGER_DWELL);

        // Add the geofences to be monitored by geofencing service.
        builder.addGeofences(geofenceList);

        // Return a GeofencingRequest.
        return builder.build();
    }

    /**
     * Gets a PendingIntent to send with the request to add or remove Geofences. Location Services
     * issues the Intent inside this PendingIntent whenever a geofence transition occurs for the
     * current list of geofences.
     *
     * @return A PendingIntent for the IntentService that handles geofence transitions.
     */
    private PendingIntent getGeofencePendingIntent() {
        if (mGeofencePendingIntent != null) {
            return mGeofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Stores whether geofences were added ore removed in {SharedPreferences};
     *
     * @param added Whether geofences were added or removed.
     */
    private void updateGeofencesAdded(boolean added) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(GEOFENCES_ADDED_KEY, added)
                .apply();
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(getParent(),
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission granted.");
                //performPendingGeofenceTask();
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
                //mPendingGeofenceTask = PendingGeofenceTask.NONE;
            }
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private void showSnackbar(final String text) {
        View container = findViewById(android.R.id.content);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onComplete(@NonNull Task<Void> task) {
        if (task.isSuccessful()) {
            updateGeofencesAdded(!getGeofencesAdded());
        } else {
            Log.d(TAG, "Error");
        }
    }

    private boolean getGeofencesAdded() {
        return PreferenceManager.getDefaultSharedPreferences(this).getBoolean(
                GEOFENCES_ADDED_KEY, false);
    }
}
