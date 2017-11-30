package madcourse.neu.edu.allot.blackbox.responders;

import madcourse.neu.edu.allot.blackbox.models.User;

/**
 * Created by zeko on 11/28/17.
 */

public interface RegisterResponder {

    /**
     * On register success.
     * @param user conatins user info
     */
    void successfulRegister(User user);

    /**
     * On register failure.
     * @param msg failure message
     */
    void failedRegister(String msg);
}
