package commaciejprogramuje.facebook.kieszonkowevulcan.Adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.R;

/**
 * Created by 5742ZGPC on 2017-10-18.
 */

public class NewsAdapter extends RecyclerView.Adapter {
    private Subjects mSubjects;

    private class NewsViewHolder extends RecyclerView.ViewHolder {
        ImageView circleImageView;
        TextView subjectTextView;
        TextView gradeTextView;
        TextView codeTextTextView;
        TextView dateTextView;
        TextView avgTextView;

        NewsViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.card_circle);
            subjectTextView = itemView.findViewById(R.id.card_subject_avg);
            gradeTextView = itemView.findViewById(R.id.card_grade_date);
            codeTextTextView = itemView.findViewById(R.id.card_code_text);
            dateTextView = itemView.findViewById(R.id.card_date);
            avgTextView = itemView.findViewById(R.id.card_avg);
        }
    }

    public NewsAdapter(Subjects pSubjects) {
        Collections.sort(pSubjects.getSubjects());
        Collections.reverse(pSubjects.getSubjects());
        mSubjects = pSubjects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Subject subject = mSubjects.getSubject(position);

        ((NewsViewHolder) holder).subjectTextView.setText(subject.getSubjectName());

        String tempAvg = subject.getSubjectAverage();
        if (tempAvg.equals("")) {
            ((NewsViewHolder) holder).avgTextView.setText("");
        } else if (tempAvg.length() == 1) {
            ((NewsViewHolder) holder).avgTextView.setText(String.format("%s,00", tempAvg));
        } else if(tempAvg.length() == 3) {
            ((NewsViewHolder) holder).avgTextView.setText(String.format("%s0", tempAvg));
        } else {
            ((NewsViewHolder) holder).avgTextView.setText(tempAvg);
        }

        String tempGrade = "";
        String tempCodeText = "";

        if (subject.getSubjectGrades().size() > 0) {
            for (int i = 0; i < subject.getSubjectGrades().size(); i++) {
                if (subject.getSubjectGrades().get(i).getmDate().equals(subject.getNewestDate())) {
                    tempGrade = tempGrade + subject.getSubjectGrades().get(i).getmGrade() + ", ";
                    tempCodeText = tempCodeText + subject.getSubjectGrades().get(i).getmCode() + " - " + subject.getSubjectGrades().get(i).getmText() + ", ";
                }
            }

            if (tempGrade.charAt(tempGrade.length() - 2) == ',') {
                tempGrade = tempGrade.substring(0, tempGrade.length() - 2);
                tempCodeText = tempCodeText.substring(0, tempCodeText.length() - 2);
            }
        } else {
            tempGrade = "--- brak ocen ---";
        }
        ((NewsViewHolder) holder).gradeTextView.setText(tempGrade);


        if (subject.getNewestDate().equals("01.01.1970")) {
            ((NewsViewHolder) holder).dateTextView.setText("");
        } else {
            ((NewsViewHolder) holder).dateTextView.setText(subject.getNewestDate().substring(0, 5));
        }


        ((NewsViewHolder) holder).codeTextTextView.setText(tempCodeText);

        int resource;
        if (!tempGrade.equals("--- brak ocen ---")) {
            int gradeInt = Integer.valueOf(tempGrade.substring(0, 1));
            if (gradeInt < 3.00) {
                resource = R.drawable.ic_circle_red;
            } else if (gradeInt < 4.00) {
                resource = R.drawable.ic_circle_yellow;
            } else if (gradeInt < 5.00) {
                resource = R.drawable.ic_circle_green;
            } else {
                resource = R.drawable.ic_circle_blue;
            }
        } else {
            Log.w("UWAGA", "white");
            resource = R.drawable.ic_circle_white;
        }
        ((NewsViewHolder) holder).circleImageView.setImageResource(resource);
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }
}
