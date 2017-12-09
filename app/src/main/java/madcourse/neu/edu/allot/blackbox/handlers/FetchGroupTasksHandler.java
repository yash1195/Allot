package madcourse.neu.edu.allot.blackbox.handlers;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import madcourse.neu.edu.allot.blackbox.api.AllotApi;
import madcourse.neu.edu.allot.blackbox.models.Group;
import madcourse.neu.edu.allot.blackbox.models.Task;
import madcourse.neu.edu.allot.blackbox.responders.FetchGroupsResponder;
import madcourse.neu.edu.allot.blackbox.responders.FetchTasksResponder;
import madcourse.neu.edu.allot.blackbox.response.FetchTasksResponse;
import madcourse.neu.edu.allot.blackbox.response.FetchUserGroupsResponse;

/**
 * Created by zeko on 12/7/17.
 */

public class FetchGroupTasksHandler {

    private static RequestParams params;
    private static AsyncHttpClient client;

    static {
        client = new AsyncHttpClient();
    }

    public FetchGroupTasksHandler() {}

    public static void doFetch(final FetchTasksResponder responder, String id, String token, String groupCode) {

        params = new RequestParams();
        params.put("id", id);
        params.put("token", token);
        params.put("groupCode", groupCode);

        client.post(AllotApi.FETCH_GROUP_TASKS, params, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

                responder.onFailedTaskFetch("Unable to fetch");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                FetchTasksResponse resp = FetchTasksResponse.parseJson(responseString);

                int status = resp.getStatus();

                if (status == 200) {

                    List<Task> tasks = resp.getTasks();

                    Log.d("AllotApi", "fetched tasks");
                    Log.d("AllotApi", tasks.toString());
                    responder.onSuccessFulTaskFetch(tasks);

                } else {
                    responder.onFailedTaskFetch("Server Error");
                }
            }
        });

    }
}
