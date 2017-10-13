package commaciejprogramuje.facebook.kieszonkowevulcan.GradesDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesDatabase.Grade;

/**
 * Created by m.szymczyk on 2017-10-11.
 */

public class Subject implements Serializable {
    private String subjectName;
    private List<Grade> subjectGrades = new ArrayList<>();
    private String subjectAverage;

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
}
