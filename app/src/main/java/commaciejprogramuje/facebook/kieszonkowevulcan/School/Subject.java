package commaciejprogramuje.facebook.kieszonkowevulcan.School;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by m.szymczyk on 2017-10-11.
 */

public class Subject implements Serializable, Comparable<Subject> {
    private String subjectName;
    private List<Grade> subjectGrades = new ArrayList<>();
    private String subjectAverage;
    private String newestDate = "01.01.1970";

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectAverage() {
        return subjectAverage;
    }

    public void setSubjectAverage(String subjectAverage) {
        this.subjectAverage = subjectAverage;
    }

    public List<Grade> getSubjectGrades() {
        return subjectGrades;
    }

    public void setSubjectGrades(List<Grade> subjectGrades) {
        this.subjectGrades = subjectGrades;
    }

    public String getNewestDate() {
        return newestDate;
    }

    public void setNewestDate() {
        if (getSubjectGrades().size() > 0) {
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

        return oldDate.compareTo(newDate);
    }

}
