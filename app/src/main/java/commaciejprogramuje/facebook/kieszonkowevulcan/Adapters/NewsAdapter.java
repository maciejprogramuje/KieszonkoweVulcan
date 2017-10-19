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
        TextView subjectAvgTextView;
        TextView gradeTextView;
        TextView codeTextTextView;
        TextView dateTextView;

        NewsViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.card_circle);
            subjectAvgTextView = itemView.findViewById(R.id.card_subject_avg);
            gradeTextView = itemView.findViewById(R.id.card_grade_date);
            codeTextTextView = itemView.findViewById(R.id.card_code_text);
            dateTextView = itemView.findViewById(R.id.card_date);
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

        String tempAvg = subject.getSubjectAverage();
        if (tempAvg.equals("")) {
            ((NewsViewHolder) holder).subjectAvgTextView.setText(subject.getSubjectName());
        } else {
            ((NewsViewHolder) holder).subjectAvgTextView.setText(subject.getSubjectName() + " (" + subject.getSubjectAverage() + ")");
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

        if(subject.getNewestDate().equals("01.01.1970")) {
            ((NewsViewHolder) holder).dateTextView.setText("");
        } else {
            ((NewsViewHolder) holder).dateTextView.setText(subject.getNewestDate());
        }

        ((NewsViewHolder) holder).codeTextTextView.setText(tempCodeText);


        //((NewsViewHolder) holder).circleImageView = subject.getCircle();

        int resource = 0;
        if (!subject.getSubjectAverage().equals("")) {

            Double avg = Double.valueOf(subject.getSubjectAverage().replace(",", "."));
            if (avg < 3.00) {
                Log.w("UWAGA", "red");
                resource = R.drawable.ic_circle_red;
            } else if (avg < 4.00) {
                Log.w("UWAGA", "yellow");
                resource = R.drawable.ic_circle_yellow;
            } else if (avg < 5.00) {
                Log.w("UWAGA", "green");
                resource = R.drawable.ic_circle_green;
            } else {
                Log.w("UWAGA", "blue");
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
