package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.GradesUtils.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.R;

/**
 * Created by 5742ZGPC on 2017-10-18.
 */

public class NewsAdapter extends RecyclerView.Adapter {
    private Subjects mSubjects;

    private class NewsViewHolder extends RecyclerView.ViewHolder {
        public ImageView circleImageView;
        public TextView subjectAvgTextView;
        public TextView gradeDateTextView;
        public TextView codeTextTextView;

        public NewsViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.card_circle);
            subjectAvgTextView = itemView.findViewById(R.id.card_subject_avg);
            gradeDateTextView = itemView.findViewById(R.id.card_grade_date);
            codeTextTextView = itemView.findViewById(R.id.card_code_text);
        }
    }

    public NewsAdapter(Subjects pSubjects) {
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

        //((NewsViewHolder) holder).circleImageView.setImageResource();

        /*
                ImageView tempImageView = (ImageView) linearLayoutArray.get(i).getChildAt(0);
                if(input[0].contains("(")) {
                    Double avg = Double.valueOf(input[0].substring(input[0].indexOf("(") + 1, input[0].indexOf(")")).replace(",", "."));
                    if(avg < 3.00) {
                        tempImageView.setImageResource(R.drawable.ic_circle_red);
                    } else if (avg < 4.00) {
                        tempImageView.setImageResource(R.drawable.ic_circle_yellow);
                    } else if(avg < 5.00) {
                        tempImageView.setImageResource(R.drawable.ic_circle_green);
                    } else {
                        tempImageView.setImageResource(R.drawable.ic_circle_blue);
                    }
                }
            }*/

        String tempAvg = subject.getSubjectAverage();

        if (tempAvg.equals("")) {
            ((NewsViewHolder) holder).subjectAvgTextView.setText(subject.getSubjectName());
        } else {
            ((NewsViewHolder) holder).subjectAvgTextView.setText(subject.getSubjectName() + " (" + subject.getSubjectAverage() + ")");
            Double avg = Double.valueOf(tempAvg.replace(",", "."));
            if (avg < 3.00) {
                ((NewsViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_red);
            } else if (avg < 4.00) {
                ((NewsViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_yellow);
            } else if (avg < 5.00) {
                ((NewsViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_green);
            } else {
                ((NewsViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_blue);
            }
        }

        if (subject.getSubjectGrades().size() > 0) {
            ((NewsViewHolder) holder).gradeDateTextView.setText(subject.getSubjectGrades().get(0).getmGrade() + " " + subject.getSubjectGrades().get(0).getmDate());
            ((NewsViewHolder) holder).codeTextTextView.setText(subject.getSubjectGrades().get(0).getmCode() + " " + subject.getSubjectGrades().get(0).getmText());
        }
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }
}
