package madcourse.neu.edu.allot.blackbox.response;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import static org.junit.Assert.*;

/**
 * Created by zeko on 11/28/17.
 */
public class LoginResponseTest {

    String LOGIN_URL = "http://allot.zeko.in/api/user/login";

    RequestParams params;
    AsyncHttpClient client;

    @Before
    public void setUp() throws Exception {

        params = new RequestParams();
        params.put("email", "test");
        params.put("password", "test");

        client = new AsyncHttpClient();

    }

    @Test
    public void parseJson() throws Exception {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                client.post(LOGIN_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        Log.d("Blackbox", "enters success");
                        LoginResponse response = LoginResponse.parseJson(responseBody.toString());
                        Log.d("Blackbox", response.getEmail());

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        Log.d("Blackbox", "failureee");
                        error.printStackTrace();
                    }
                });
            }
        });


    }

}