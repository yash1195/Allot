package madcourse.neu.edu.allot.blackbox.responders;

/**
 * Created by zeko on 12/6/17.
 */

public interface JoinGroupResponder {

    void onSuccessfulJoinGroup();

    void onFailedJoinGroup(String msg);
}
