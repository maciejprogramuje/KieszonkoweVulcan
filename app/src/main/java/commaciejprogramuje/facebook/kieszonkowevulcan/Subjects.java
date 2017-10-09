package commaciejprogramuje.facebook.kieszonkowevulcan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.szymczyk on 2017-10-09.
 */

public class Subjects implements Serializable {
    private class Subject implements Serializable {
        private String subjectName;
        private String subjectGrades;
        private String subjectAverage;

        Subject(String subjectName) {
            this.subjectName = subjectName;
            subjectGrades = "";
            subjectAverage = "";
        }

        public String getSubjectName() {
            return subjectName;
        }

        public void setSubjectName(String subjectName) {
            this.subjectName = subjectName;
        }

        public String getSubjectGrades() {
            return subjectGrades;
        }

        public void setSubjectGrades(String subjectGrades) {
            this.subjectGrades = subjectGrades;
        }

        public String getSubjectAverage() {
            return subjectAverage;
        }

        public void setSubjectAverage(String subjectAverage) {
            this.subjectAverage = subjectAverage;
        }
    }

    private List<Subject> subjects = new ArrayList<>();

    Subjects() {
        subjects.add(new Subject("Język polski"));
        subjects.add(new Subject("Język angielski"));
        subjects.add(new Subject("Język niemiecki"));
        subjects.add(new Subject("Muzyka"));
        subjects.add(new Subject("Historia"));
        subjects.add(new Subject("Wiedza o społeczeństwie"));
        subjects.add(new Subject("Geografia"));
        subjects.add(new Subject("Biologia"));
        subjects.add(new Subject("Chemia"));
        subjects.add(new Subject("Fizyka"));
        subjects.add(new Subject("Matematyka"));
        subjects.add(new Subject("Informatyka"));
        subjects.add(new Subject("Edukacja dla bezpieczeństwa"));
        subjects.add(new Subject("Zajęcia techniczne"));
        subjects.add(new Subject("Wychowanie do życia w rodzinie"));
        subjects.add(new Subject("Etyka"));
    }

    public String getName(int index) {
        return subjects.get(index).getSubjectName();
    }

    public String getGrades(int index) {
        return subjects.get(index).getSubjectGrades();
    }

    public String getAverage(int index) {
        return subjects.get(index).getSubjectAverage();
    }

    public void setGrades(int index, String gradesString) {
        subjects.get(index).setSubjectGrades(gradesString);
    }

    public void setAverage(int index, String averageString) {
        subjects.get(index).setSubjectAverage(averageString);
    }
}
