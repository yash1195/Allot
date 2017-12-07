package madcourse.neu.edu.allot.place;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.models.Group;
import madcourse.neu.edu.allot.blackbox.models.User;
import madcourse.neu.edu.allot.participant.ParticipantActivity;

public class DashboardFragment extends Fragment {

    private ListView participantList;
    private ArrayList<String> list;
    private CardAdapter cardAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        /*cardList = new ArrayList<>();
        participantList = rootView.findViewById(R.id.list_participants);
        adapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, cardList);
        participantList.setAdapter(adapter);*/

        Group groupData = (Group) getArguments().getSerializable("groupData");

        list = new ArrayList<>();

        for (User member: groupData.getMembers()) {
            list.add(member.getFirstName() + " " + member.getLastName());
        }

        cardAdapter = new CardAdapter(list, rootView.getContext(),
                R.layout.card_participant, ParticipantActivity.class);
        participantList = rootView.findViewById(R.id.list_participants);
        participantList.setAdapter(cardAdapter);

        return rootView;
    }
}
