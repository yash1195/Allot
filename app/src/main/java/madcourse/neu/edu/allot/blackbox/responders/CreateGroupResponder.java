package madcourse.neu.edu.allot.blackbox.responders;

/**
 * Created by zeko on 12/6/17.
 */

public interface CreateGroupResponder {

    void onSuccessfulCreateGroup(String groupCode);

    void onFailedCreateGroup(String msg);
}
