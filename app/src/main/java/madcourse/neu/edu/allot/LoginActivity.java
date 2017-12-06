package madcourse.neu.edu.allot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import madcourse.neu.edu.allot.blackbox.handlers.LoginHandler;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.blackbox.responders.LoginResponder;
import madcourse.neu.edu.allot.group.GroupActivity;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoginResponder {

    AsyncHttpClient client;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // instantiate stuff
        client = new AsyncHttpClient();

        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);


        // sign in
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                // hide keyboard
                hideSoftKeyboard(LoginActivity.this);

                LoginHandler.doLogin(LoginActivity.this,
                        mEmailView.getText().toString(),
                        mPasswordView.getText().toString());

            }
        });

        // go to registration activity
        Button goToRegistrationBtn = (Button) findViewById(R.id.go_to_register_button);
        goToRegistrationBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {

                // go to registration activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * On login success.
     *
     * @param user conatins user info
     */
    @Override
    public void successfulLogin(User user) {

        // store user details in shared preference
        SharedPreferences.Editor editor = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE).edit();

        editor.putString(User.SHARED_PREF_TAG_FIRST_NAME, user.getFirstName());
        editor.putString(User.SHARED_PREF_TAG_LAST_NAME, user.getLastName());
        editor.putString(User.SHARED_PREF_TAG_EMAIL, user.getEmail());
        editor.putString(User.SHARED_PREF_TAG_TOKEN, user.getToken());
        editor.putString(User.SHARED_PREF_TAG_ID, user.getId());

        editor.commit();

        Intent intent = new Intent(getApplicationContext(), GroupActivity.class);
        startActivity(intent);
    }

    /**
     * On login failure.
     *
     * @param msg failure message
     */
    @Override
    public void failedLogin(String msg) {

        Context context = getApplicationContext();

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, msg, duration);
        toast.show();
    }
}

