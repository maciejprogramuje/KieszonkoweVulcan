package commaciejprogramuje.facebook.kieszonkowevulcan.gim_16;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Subject implements Serializable, Comparable<Subject> {
    private String subjectName;
    private List<Grade> subjectGrades = new ArrayList<>();
    private String subjectAverage;
    private String subjectProposition;
    private String subjectSem;
    private String newestDate = "01.01.1970";

    Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectAverage() {
        return subjectAverage;
    }

    void setSubjectAverage(String subjectAverage) {
        this.subjectAverage = subjectAverage;
    }

    public List<Grade> getSubjectGrades() {
        return subjectGrades;
    }

    void setSubjectGrades(List<Grade> subjectGrades) {
        this.subjectGrades = subjectGrades;
    }

    public String getNewestDate() {
        return newestDate;
    }

    public String getSubjectProposition() {
        return subjectProposition;
    }

    void setSubjectProposition(String subjectProposition) {
        this.subjectProposition = subjectProposition;
    }

    public String getSubjectSem() {
        return subjectSem;
    }

    void setSubjectSem(String subjectSem) {
        this.subjectSem = subjectSem;
    }

    void setNewestDate() {
        if (getSubjectGrades().size() > 0 && !getSubjectGrades().get(0).getmDate().equals("")) {
            Calendar oldGradeDateCal = Calendar.getInstance();
            try {
                String dateString = getSubjectGrades().get(0).getmDate();
                Date date = (new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString));
                oldGradeDateCal.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < getSubjectGrades().size(); i++) {
                Calendar newGradeDateCal = Calendar.getInstance();
                try {
                    String dateString = getSubjectGrades().get(i).getmDate();
                    Date date = (new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(dateString));
                    newGradeDateCal.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (newGradeDateCal.after(oldGradeDateCal)) {
                    oldGradeDateCal = newGradeDateCal;
                    newestDate = "01.01.1970";
                }
                newestDate = getSubjectGrades().get(i).getmDate();
            }
        }
    }

    @Override
    public int compareTo(@NonNull Subject subject) {
        Date oldDate = null;
        Date newDate = null;

        try {
            oldDate = new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(getNewestDate());
            newDate = new SimpleDateFormat("dd.MM.yyyy", Locale.US).parse(subject.getNewestDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert oldDate != null;
        return oldDate.compareTo(newDate);
    }

}
