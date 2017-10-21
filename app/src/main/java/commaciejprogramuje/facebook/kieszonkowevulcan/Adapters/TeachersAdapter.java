package commaciejprogramuje.facebook.kieszonkowevulcan.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import commaciejprogramuje.facebook.kieszonkowevulcan.R;
import commaciejprogramuje.facebook.kieszonkowevulcan.SchoolUtils.Teacher;

/**
 * Created by 5742ZGPC on 2017-10-21.
 */

public class TeachersAdapter extends RecyclerView.Adapter {
    private ArrayList<Teacher> mTeachers;

    private class TeachersViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView substituteTextView;
        TextView onDutyTextView;

        TeachersViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.card_teacher_name);
            substituteTextView = itemView.findViewById(R.id.card_teacher_substitute);
            onDutyTextView = itemView.findViewById(R.id.card_teacher_onduty);
        }
    }

    public TeachersAdapter(ArrayList<Teacher> pTeachers) {
        mTeachers = pTeachers;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_teacher, parent, false);
        return new TeachersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Teacher teacher = mTeachers.get(position);

        ((TeachersViewHolder) holder).nameTextView.setText(teacher.getNameTeacher());
        ((TeachersViewHolder) holder).substituteTextView.setText(teacher.getSubstituteTeacher());
        ((TeachersViewHolder) holder).onDutyTextView.setText(teacher.getOnDutyTeacher());


    }

    @Override
    public int getItemCount() {
        return mTeachers.size();
    }
}
