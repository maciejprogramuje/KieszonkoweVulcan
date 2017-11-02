package commaciejprogramuje.facebook.kieszonkowevulcan.fragments_adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;

import commaciejprogramuje.facebook.kieszonkowevulcan.R;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subjects;

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
            circleImageView = itemView.findViewById(R.id.card_news_circle);
            subjectTextView = itemView.findViewById(R.id.card_news_subject_avg);
            gradeTextView = itemView.findViewById(R.id.card_news_grade_date);
            codeTextTextView = itemView.findViewById(R.id.card_news_code_text);
            dateTextView = itemView.findViewById(R.id.card_news_date);
            avgTextView = itemView.findViewById(R.id.card_news_avg);
        }
    }

    public NewsAdapter(Subjects pSubjects) {
        Collections.sort(pSubjects.getSubjectsArray());
        Collections.reverse(pSubjects.getSubjectsArray());
        mSubjects = pSubjects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Subject subject = mSubjects.getOneFromSubjects(position);

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
                    tempGrade = tempGrade + subject.getSubjectGrades().get(i).getmGrade();
                    tempCodeText = tempCodeText + subject.getSubjectGrades().get(i).getmCode() + " - " + subject.getSubjectGrades().get(i).getmText();

                    if (i < subject.getSubjectGrades().size() - 1) {
                        tempGrade += ", ";
                        tempCodeText += "\n";
                    }
                }
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

        if (!tempGrade.equals("--- brak ocen ---")) {
            int gradeInt = Integer.valueOf(tempGrade.substring(0, 1));
            if (gradeInt < 3.00) {
                ((NewsViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_red_new);
            } else if (gradeInt < 4.00) {
                ((NewsViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_yellow_new);
            } else if (gradeInt < 5.00) {
                ((NewsViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_green_new);
            } else {
                ((NewsViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_blue_new);
            }
        } else {
            ((NewsViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_white);
        }
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }
}
