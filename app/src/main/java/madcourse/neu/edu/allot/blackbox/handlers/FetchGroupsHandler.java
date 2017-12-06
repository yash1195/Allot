package madcourse.neu.edu.allot.blackbox.handlers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import madcourse.neu.edu.allot.blackbox.api.AllotApi;
import madcourse.neu.edu.allot.blackbox.models.Group;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.blackbox.responders.FetchGroupsResponder;
import madcourse.neu.edu.allot.blackbox.responders.LoginResponder;
import madcourse.neu.edu.allot.blackbox.response.FetchUserGroupsResponse;
import madcourse.neu.edu.allot.blackbox.response.LoginResponse;

/**
 * Created by zeko on 12/6/17.
 */

public class FetchGroupsHandler {

    private static RequestParams params;
    private static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
    }

    public FetchGroupsHandler() {


    }

    public static void doFetch(final FetchGroupsResponder responder, String id, String token) {

        params = new RequestParams();
        params.put("id", id);
        params.put("token", token);

        client.post(AllotApi.FETCH_USER_GROUPS, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                responder.onFailedGroupsFetch("Error");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.d("AllotApi", responseString);

                FetchUserGroupsResponse resp = FetchUserGroupsResponse.parseJson(responseString);

                int status = resp.getStatus();

                Log.d("AllotApi", "" + resp.getGroups().get(0).getCode());


                if (status == 200) {

//                    User user = new User();
//
//                    // add user information
//                    user.setFirstName(resp.getFirstName());
//                    user.setLastName(resp.getLastName());
//                    user.setEmail(resp.getEmail());
//                    user.setId(resp.getId());
//                    user.setEmail(resp.getEmail());
//                    user.setToken(resp.getToken());
//
//                    responder.successfulLogin(user);
                } else {
//                    responder.failedLogin("Invalid username/password");
                }
            }
        });

    }
}
