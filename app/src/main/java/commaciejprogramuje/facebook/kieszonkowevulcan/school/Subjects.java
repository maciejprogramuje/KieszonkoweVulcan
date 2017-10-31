package commaciejprogramuje.facebook.kieszonkowevulcan.school;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.szymczyk on 2017-10-09.
 */

public class Subjects implements Serializable {
    private List<Subject> subjects;

    public Subjects() {
        subjects = new ArrayList<>();
        subjects.add(new Subject("Język polski")); // 0
        subjects.add(new Subject("Język angielski")); // 1
        subjects.add(new Subject("Język niemiecki")); // 2
        subjects.add(new Subject("Muzyka")); // 3
        subjects.add(new Subject("Historia")); // 4
        subjects.add(new Subject("Wiedza o społeczeństwie")); // 5
        subjects.add(new Subject("Geografia")); // 6
        subjects.add(new Subject("Biologia")); // 7
        subjects.add(new Subject("Chemia")); // 8
        subjects.add(new Subject("Fizyka")); // 9
        subjects.add(new Subject("Matematyka")); // 10
        subjects.add(new Subject("Informatyka")); // 11
        subjects.add(new Subject("Edukacja dla bezpieczeństwa")); // 12
        subjects.add(new Subject("Zajęcia techniczne")); // 13
        subjects.add(new Subject("Wychowanie do życia w rodzinie")); // 14
        subjects.add(new Subject("Etyka")); // 15
    }

    public String getName(int index) {
        return subjects.get(index).getSubjectName();
    }

    public List<Grade> getGradesFromOneSubject(int index) {
        return subjects.get(index).getSubjectGrades();
    }

    public String getAverage(int index) {
        return subjects.get(index).getSubjectAverage();
    }

    public void setGrades(int index, List<Grade> gradesList) {
        subjects.get(index).setSubjectGrades(gradesList);
    }

    public void setAverage(int index, String averageString) {
        subjects.get(index).setSubjectAverage(averageString);
    }

    public void setNewestDate(int index) {
        subjects.get(index).setNewestDate();
    }

    public int size() {
        return subjects.size();
    }

    public List<Subject> getSubjectsArray() {
        return subjects;
    }

    public void setSubjectsArray(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Subject getOneFromSubjects(int index) {
        return subjects.get(index);
    }
}
