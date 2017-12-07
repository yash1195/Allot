package madcourse.neu.edu.allot.task;

import madcourse.neu.edu.allot.blackbox.models.User;

public class CheckboxModel {
    User user;
    boolean checked;

    public CheckboxModel(User user) {
        this.user = user;
        checked = false;
    }

}
