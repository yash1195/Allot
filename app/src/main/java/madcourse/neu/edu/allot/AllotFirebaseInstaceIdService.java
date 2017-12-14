package madcourse.neu.edu.allot;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import madcourse.neu.edu.allot.blackbox.models.User;

/**
 * Created by zeko on 12/14/17.
 */

public class AllotFirebaseInstaceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String tkn = FirebaseInstanceId.getInstance().getToken();

        SharedPreferences.Editor editor = getSharedPreferences(User.SHARED_PREF_GROUP, MODE_PRIVATE).edit();
        editor.putString(User.SHARED_PREF_TAG_DEVICE_ID, tkn);
        editor.commit();
    }


}
