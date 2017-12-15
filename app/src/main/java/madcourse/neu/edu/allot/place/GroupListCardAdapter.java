package madcourse.neu.edu.allot.place;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import madcourse.neu.edu.allot.R;
import madcourse.neu.edu.allot.blackbox.models.Group;

public class GroupListCardAdapter extends BaseAdapter implements ListAdapter {

    private List<Group> groupList = new ArrayList<>();
    private Context context;
    private int layout;
    private Class activity;

    public GroupListCardAdapter(List<Group> list, Context context, int layout, Class activity) {

        this.groupList = list;
        this.context = context;
        this.layout = layout;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int pos) {
        return groupList.get(pos);
    }

    @Override
    public long getItemId(int pos) {

        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView) view.findViewById(R.id.label_card_title);

        listItemText.setText(groupList.get(position).getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity);
                intent.putExtra("place", groupList.get(position).getName());

                intent.putExtra("groupData", groupList.get(position));

                context.startActivity(intent);
            }
        });
        return view;
    }
}
