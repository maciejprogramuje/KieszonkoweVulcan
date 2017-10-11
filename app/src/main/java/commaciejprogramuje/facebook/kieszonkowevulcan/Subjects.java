package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.szymczyk on 2017-10-09.
 */

class Subjects implements Serializable {
    private List<Subject> subjects = new ArrayList<>();

    Subjects() {
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

    public List<Grade> getGrades(int index) {
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

    public int size() {
        return subjects.size();
    }
}
