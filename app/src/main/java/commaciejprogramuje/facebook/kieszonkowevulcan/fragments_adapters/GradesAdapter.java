package commaciejprogramuje.facebook.kieszonkowevulcan.fragments_adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import commaciejprogramuje.facebook.kieszonkowevulcan.R;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subjects;

public class GradesAdapter extends RecyclerView.Adapter {
    private Subjects mSubjects;

    private class GradesViewHolder extends RecyclerView.ViewHolder {
        ImageView circleImageView;
        TextView subjectTextView;
        TextView gradeCodeTextTextView;
        TextView avgTextView;
        TextView propositionTextView;
        TextView semTextView;

        GradesViewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.card_grades_circle);
            subjectTextView = itemView.findViewById(R.id.card_grades_subject);
            gradeCodeTextTextView = itemView.findViewById(R.id.card_grades_grades);
            avgTextView = itemView.findViewById(R.id.card_grades_avg);
            propositionTextView = itemView.findViewById(R.id.card_grades_prop);
            semTextView = itemView.findViewById(R.id.card_grades_sem);
        }
    }

    public GradesAdapter(Subjects pSubjects) {
        mSubjects = pSubjects;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_grades, parent, false);
        return new GradesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Subject subject = mSubjects.getOneFromSubjects(position);

        ((GradesViewHolder) holder).subjectTextView.setText(subject.getSubjectName());

        String tempProposition = subject.getSubjectProposition();
        if(tempProposition == null || tempProposition.equals("")) {
            ((GradesViewHolder) holder).propositionTextView.setText("");
        } else {
            ((GradesViewHolder) holder).propositionTextView.setText(String.format("prop: %s", tempProposition));
        }

        String tempSem = subject.getSubjectSem();
        if(tempSem == null || tempSem.equals("")) {
            ((GradesViewHolder) holder).semTextView.setText("");
        } else {
            ((GradesViewHolder) holder).semTextView.setText(String.format("sem: %s", tempSem));
        }

        String tempAvg = subject.getSubjectAverage();
        if (tempAvg == null || tempAvg.equals("")) {
            ((GradesViewHolder) holder).avgTextView.setText("");
        } else if (tempAvg.length() == 1) {
            ((GradesViewHolder) holder).avgTextView.setText(String.format("śr: %s,00", tempAvg));
        } else if (tempAvg.length() == 3) {
            ((GradesViewHolder) holder).avgTextView.setText(String.format("śr: %s0", tempAvg));
        } else {
            ((GradesViewHolder) holder).avgTextView.setText(String.format("śr: %s", tempAvg));
        }

        StringBuilder gradeDateCodeText = new StringBuilder();

        if (subject.getSubjectGrades().size() > 0) {
            for (int i = subject.getSubjectGrades().size() - 1; i >= 0; i--) {

                gradeDateCodeText.append(subject.getSubjectGrades().get(i).getmGrade())
                        .append(" (")
                        .append(subject.getSubjectGrades().get(i).getmDate().substring(0, 5)).append(") ")
                        .append(subject.getSubjectGrades().get(i).getmCode())
                        .append(", w:")
                        .append(subject.getSubjectGrades().get(i).getmWeight())
                        .append(" - ")
                        .append(subject.getSubjectGrades().get(i).getmText());
                if (i > 0) {
                    gradeDateCodeText.append("\n");
                }
            }
        } else {
            gradeDateCodeText = new StringBuilder("--- brak ocen ---");
        }

        Log.w("UWAGA","przedmiot: " + gradeDateCodeText.toString());
        ((GradesViewHolder) holder).gradeCodeTextTextView.setText(gradeDateCodeText.toString());


        if (tempAvg != null && !tempAvg.equals("")) {
            Double avgDouble = Double.valueOf(tempAvg.replace(",", "."));
            if (avgDouble < 3.00) {
                ((GradesViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_red_avg);
            } else if (avgDouble < 4.00) {
                ((GradesViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_yellow_avg);
            } else if (avgDouble < 5.00) {
                ((GradesViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_green_avg);
            } else {
                ((GradesViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_blue_avg);
            }
        } else {
            ((GradesViewHolder) holder).circleImageView.setImageResource(R.drawable.ic_circle_white);
        }


    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }
}
