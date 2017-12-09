package madcourse.neu.edu.allot.blackbox.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeko on 12/6/17.
 */

public class Group implements Serializable {

    private String name = "";
    private String code = "";
    private String id = "";
    private List<User> members;

    public Group() {
        members = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String value) {
        id = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String value) {
        code = value;
    }

    public void setMembers(List<User> users) {
        members = users;
    }

    public List<User> getMembers() {
        return members;
    }
}
