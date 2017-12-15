package madcourse.neu.edu.allot.task;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import madcourse.neu.edu.allot.BuildConfig;
import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.handlers.CreateTaskHandler;
import madcourse.neu.edu.allot.blackbox.models.Group;
import madcourse.neu.edu.allot.blackbox.models.User;

public class AddTaskActivity extends AppCompatActivity implements OnCompleteListener<Void>,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private EditText taskNameView;
    private EditText descriptionView;
    private EditText locationView;
    private EditText setTimeView;
    private EditText radiusView;
    private Button buttonParticipant;
    private Button buttonLocation;
    private ListView allotList;
    private Button buttonTime;
    private List<CheckboxModel> users;
    private CheckboxAdapter checkboxAdapter;

    // Task date-time data
    private int day, month, year, hour, minute;

    private LatLng selectedLatLng;
    private String nameOfLocation = "";

    // group data
    private Group groupData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);


        // retrieve group data
        groupData = (Group) getIntent().getExtras().get("groupData");

        // geo service
        selectedLatLng = null;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getStringExtra("place"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        geofenceList = new ArrayList<>();

        // task details
        taskNameView = (EditText) findViewById(R.id.editText_taskname);
        descriptionView = (EditText) findViewById(R.id.editText_description);
        locationView = (EditText) findViewById(R.id.text_location);
        setTimeView = (EditText) findViewById(R.id.editText_set_time);
        radiusView = (EditText) findViewById(R.id.editText_radius);
        buttonLocation = (Button) findViewById(R.id.button_choose_location);
        buttonTime = (Button) findViewById(R.id.button_set_time);
        buttonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this,
                        AddTaskActivity.this,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        allotList = (ListView) findViewById(R.id.list_allot_participant);

        // This is where you pass the list of participants in the group
        // I am passing in an object called CheckboxModel which contains a User instance and a boolean
        // this way, we can know which participants were selected when the task is created.
        users = new ArrayList<>();

        for (User member : groupData.getMembers()) {
            users.add(new CheckboxModel(member));
        }

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
                locationView.setText(nameOfLocation);
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
    private static float GEOFENCE_RADIUS_IN_METERS = 50;
    private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * 60 * 60 * 1000;
    private static final String GEOFENCES_ADDED_KEY = "com.google.android.gms.locationView.Geofence";
    private PendingIntent mGeofencePendingIntent;
    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList;

    /**
     * Called when user clicks "Add" to create the task.
     *
     * @param item item
     * @return false, blocks creating a task
     */
    @SuppressWarnings("MissingPermission")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.action_add) {

            if (!checkRequirements()) {
                return false;
            }
            if (selectedLatLng != null) {
                GEOFENCE_RADIUS_IN_METERS = Float.parseFloat(radiusView.getText().toString());
                geofenceList.add(new Geofence.Builder()
                        .setRequestId(nameOfLocation)
                        .setCircularRegion(
                                selectedLatLng.latitude,
                                selectedLatLng.longitude,
                                GEOFENCE_RADIUS_IN_METERS
                        )
                        .setExpirationDuration(GEOFENCE_EXPIRATION_IN_MILLISECONDS)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build());
                geofencingClient = LocationServices.getGeofencingClient(this);

                geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent())
                        .addOnCompleteListener(this);
            }

            createTask();

            finish();
            return true;
        }else if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Checks requirements to create a task.
     *
     * @return true if all requirements are satisfied
     */
    public boolean checkRequirements() {
        if (!checkPermissions()) {
            requestPermissions();
            return false;
        }
        // check if Task title is empty
        if (taskNameView.getText().toString().trim().isEmpty()) {
            showDialog("Incomplete", "Task name is required");
            return false;
        }
        // check if Time is provided
        if (setTimeView.getText().toString().trim().isEmpty()) {
            showDialog("Incomplete", "Time required to create task");
            return false;
        }
        // check if at least one participant is selected
        if (!participantSelected()) {
            showDialog("Incomplete", "Allot task to at least one participant");
            return false;
        }
        // check if the task is scheduled at a valid time
        if (!scheduleAlarm()) {
            return false;
        }
        return true;
    }

    public boolean participantSelected() {
        for (CheckboxModel user : users) {
            if (user.checked) {
                return true;
            }
        }
        return false;
    }

    /**
     * Shows a dialog with the provided title and message.
     *
     * @param title   title of the dialog
     * @param message dialog message
     */
    public void showDialog(String title, String message) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.label_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        final Dialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Creates a task object with user inputs.
     */
    public void createTask() {

        madcourse.neu.edu.allot.blackbox.models.Task createdTask = new madcourse.neu.edu.allot.blackbox.models.Task();

        // task participants
        List<User> participants = new ArrayList<>();
        for (CheckboxModel checkOption : users) {
            if (checkOption.checked) {
                participants.add(checkOption.user);
            }
        }
        createdTask.setParticipants(participants);

        // title
        createdTask.setTitle(taskNameView.getText().toString());

        // description
        createdTask.setDescription(descriptionView.getText().toString());

        // location
        createdTask.setLatitude(selectedLatLng.latitude);
        createdTask.setLongitude(selectedLatLng.longitude);
        if (nameOfLocation.length() != 0) {
            createdTask.setIsLocationEnabled(true);
        }

        // time
        Date taskDateTime = new Date(year, month, day, hour, minute);
        createdTask.setTime(Long.toString(taskDateTime.getTime()));

        // group code
        createdTask.setGroupCode(groupData.getCode());

        // requestor id-token
        SharedPreferences sharedPref = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE);
        String requestorId = sharedPref.getString(User.SHARED_PREF_TAG_ID, "NA");
        String requestorToken = sharedPref.getString(User.SHARED_PREF_TAG_TOKEN, "NA");

        CreateTaskHandler.createTask(createdTask, requestorId, requestorToken);

    }

    public boolean scheduleAlarm() {

        Calendar calendar = Calendar.getInstance();

        if (calendar.get(Calendar.YEAR) > year || calendar.get(Calendar.MONTH) > month ||
                calendar.get(Calendar.DAY_OF_MONTH) > day || calendar.get(Calendar.HOUR) > hour ||
                calendar.get(Calendar.MINUTE) > minute) {
            Toast.makeText(this, "Cannot schedule a task in the past",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        //TODO: add day, month, hour
        int min = minute - calendar.get(Calendar.MINUTE);
        Long time = new GregorianCalendar().getTimeInMillis() + 60 * 1000;
        Intent intentAlarm = new Intent(this, AlarmBroadcastReceiver.class);
        intentAlarm.putExtra("task", taskNameView.getText());
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //set the alarm for particular time
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(this,
                1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        Toast.makeText(this, "Alarm Scheduled for ", Toast.LENGTH_LONG).show();
        return true;
    }

    public GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);

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
        intent.putExtra("task", taskNameView.getText().toString());
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

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        year = i;
        month = i1 + 1;
        day = i2;

        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this,
                AddTaskActivity.this, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hour = i;
        minute = i1;
        setTimeView.setText(i + ":" + i1 + " on " + month + "/" + day + "/" + year);
    }
}
