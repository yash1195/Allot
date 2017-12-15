package madcourse.neu.edu.allot;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import madcourse.neu.edu.allot.blackbox.handlers.RegisterHandler;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.blackbox.responders.RegisterResponder;
import madcourse.neu.edu.allot.group.GroupActivity;
import madcourse.neu.edu.allot.place.PlaceActivity;


/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements RegisterResponder {


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // get registration details
        final EditText firstNameView = (EditText) findViewById(R.id.firstNameRegistration);
        final EditText lastNameView = (EditText) findViewById(R.id.lastNameRegistration);
        final EditText emailView = (EditText) findViewById(R.id.emailRegistration);
        final EditText passwordView = (EditText) findViewById(R.id.passwordRegistration);

        // android device id
        SharedPreferences sharedPref = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE);
        final String androidDeviceId = sharedPref.getString(User.SHARED_PREF_TAG_DEVICE_ID, "NA");


        // register
        Button userRegisterBtn = (Button) findViewById(R.id.register_user_btn);
        userRegisterBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                // hide keyboard
                hideSoftKeyboard(RegisterActivity.this);

                RegisterHandler.doRegister(RegisterActivity.this,
                        emailView.getText().toString(),
                        passwordView.getText().toString(),
                        firstNameView.getText().toString(),
                        lastNameView.getText().toString(),
                        androidDeviceId);
            }
        });



        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * On register success.
     *
     * @param user conatins user info
     */
    @Override
    public void successfulRegister(User user) {

        // store user details in shared preference
        SharedPreferences.Editor editor = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE).edit();

        editor.putString(User.SHARED_PREF_TAG_FIRST_NAME, user.getFirstName());
        editor.putString(User.SHARED_PREF_TAG_LAST_NAME, user.getLastName());
        editor.putString(User.SHARED_PREF_TAG_EMAIL, user.getEmail());
        editor.putString(User.SHARED_PREF_TAG_TOKEN, user.getToken());
        editor.putString(User.SHARED_PREF_TAG_ID, user.getId());

        editor.commit();

//        setSessionActive();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * On register failure.
     *
     * @param msg failure message
     */
    @Override
    public void failedRegister(String msg) {

        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }

    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void setSessionActive() {

        SharedPreferences.Editor editor = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE).edit();

        editor.putString(User.SHARED_PREF_TAG_LOGGED_IN_SESSION, "1");

        editor.commit();
    }
}

