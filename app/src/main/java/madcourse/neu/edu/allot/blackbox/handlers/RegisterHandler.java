package madcourse.neu.edu.allot.blackbox.handlers;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import madcourse.neu.edu.allot.blackbox.api.AllotApi;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.blackbox.responders.RegisterResponder;
import madcourse.neu.edu.allot.blackbox.response.LoginResponse;

/**
 * Handles user registration.
 */
public class RegisterHandler {


    private static RequestParams params;
    private static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
    }

    public static void doRegister(final RegisterResponder responder,
                                  String email,
                                  String password,
                                  String firstName,
                                  String lastName,
                                  String androidDeviceId) {

        params = new RequestParams();
        params.put("email", email);
        params.put("password", password);
        params.put("firstName", firstName);
        params.put("lastName", lastName);

        params.put("androidDeviceId", androidDeviceId);

        client.post(AllotApi.REGISTER_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                responder.failedRegister("Error");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                LoginResponse resp = LoginResponse.parseJson(responseString);

                int status = resp.getStatus();

                if (status == 200) {

                    User user = new User();

                    // add user information
                    user.setFirstName(resp.getFirstName());
                    user.setLastName(resp.getLastName());
                    user.setEmail(resp.getEmail());
                    user.setId(resp.getId());
                    user.setEmail(resp.getEmail());
                    user.setToken(resp.getToken());

                    responder.successfulRegister(user);
                } else if (status == 409) {
                    responder.failedRegister("Email already exists");
                }
                else {
                    responder.failedRegister("Registration failed");
                }
            }
        });
    }
}
