package madcourse.neu.edu.allot.blackbox.responders;

import java.util.List;

import madcourse.neu.edu.allot.blackbox.models.Group;

/**
 * Created by zeko on 12/6/17.
 */

public interface FetchGroupsResponder {

    void onSuccessfullGroupsFetch(List<Group> groups);

    void onFailedGroupsFetch(String msg);
}
