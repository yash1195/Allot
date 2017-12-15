package madcourse.neu.edu.allot.blackbox.handlers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import madcourse.neu.edu.allot.blackbox.api.AllotApi;
import madcourse.neu.edu.allot.blackbox.responders.CreateGroupResponder;
import madcourse.neu.edu.allot.blackbox.responders.JoinGroupResponder;
import madcourse.neu.edu.allot.blackbox.response.GenericResponse;
import madcourse.neu.edu.allot.blackbox.response.SuccessfulCreateGroupResponse;

/**
 * Created by zeko on 12/6/17.
 */

public class JoinGroupHandler {

    private static RequestParams params;
    private static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
    }

    public static void doJoin(final JoinGroupResponder responder, String userId, String userToken, String groupCode) {

        params = new RequestParams();
        params.put("id", userId);
        params.put("token", userToken);
        params.put("groupCode", groupCode);

        client.post(AllotApi.JOIN_GROUP, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                responder.onFailedJoinGroup("Error");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                GenericResponse resp = GenericResponse.parseJson(responseString);

                int status = resp.getStatus();

                if (status == 200) {

                    responder.onSuccessfulJoinGroup();

                } else if (status == 403) {
                    responder.onFailedJoinGroup(resp.getMsg());
                } else {
                    responder.onFailedJoinGroup("Failed to Join Group");
                }
            }
        });

    }
}
