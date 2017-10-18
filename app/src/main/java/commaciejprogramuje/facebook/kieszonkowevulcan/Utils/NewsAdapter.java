package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

        String tempGradeDate = "";
        String tempCodeText = "";

        if (subject.getSubjectGrades().size() > 0) {

            Calendar oldGradeDateCal = Calendar.getInstance();
            try {
                String dateString = subject.getSubjectGrades().get(0).getmDate();
                Date date = (new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString));
                oldGradeDateCal.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < subject.getSubjectGrades().size(); i++) {
                Calendar newGradeDateCal = Calendar.getInstance();
                try {
                    String dateString = subject.getSubjectGrades().get(i).getmDate();
                    Date date = (new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString));
                    newGradeDateCal.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (newGradeDateCal.after(oldGradeDateCal)) {
                    oldGradeDateCal = newGradeDateCal;
                    tempGradeDate = "";
                    tempCodeText = "";
                }

                tempGradeDate = tempGradeDate + subject.getSubjectGrades().get(i).getmGrade() + ", ";
                tempCodeText = tempCodeText + subject.getSubjectGrades().get(i).getmCode() + " - " + subject.getSubjectGrades().get(i).getmText() + ", ";
            }

            if(tempGradeDate.charAt(tempGradeDate.length() - 2) == ',') {
                tempGradeDate = tempGradeDate.substring(0, tempGradeDate.length() - 2);
                tempCodeText = tempCodeText.substring(0, tempCodeText.length() - 2);
            }

            tempGradeDate = tempGradeDate + " (" + subject.getSubjectGrades().get(subject.getSubjectGrades().size() - 1).getmDate() + ")";
        } else {
            tempGradeDate = "--- brak ocen ---";
        }

        ((NewsViewHolder) holder).gradeDateTextView.setText(tempGradeDate);
        ((NewsViewHolder) holder).codeTextTextView.setText(tempCodeText);
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }
}
