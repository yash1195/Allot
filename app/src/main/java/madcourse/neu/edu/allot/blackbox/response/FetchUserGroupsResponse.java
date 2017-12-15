package madcourse.neu.edu.allot.blackbox.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import madcourse.neu.edu.allot.blackbox.models.Group;

/**
 * Created by zeko on 12/6/17.
 */

public class FetchUserGroupsResponse {

    private int status;
    private String msg;
    private List<Group> groups;

    public FetchUserGroupsResponse() {
        groups = new ArrayList<>();
    }



    public Integer getStatus() {
        return status;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> value) {
        groups = value;
    }

    public static FetchUserGroupsResponse parseJson(String response) {

        Gson gson = new GsonBuilder().create();
        FetchUserGroupsResponse fetchUserGroupsResponse = gson.fromJson(response, FetchUserGroupsResponse.class);

        return fetchUserGroupsResponse;
    }
}
