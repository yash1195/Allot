package madcourse.neu.edu.allot.blackbox.responders;

import madcourse.neu.edu.allot.blackbox.models.User;

/**
 * Created by zeko on 11/28/17.
 */

public interface LoginResponder {

    /**
     * On login success.
     * @param user conatins user info
     */
    void successfulLogin(User user);

    /**
     * On login failure.
     * @param msg failure message
     */
    void failedLogin(String msg);
}
