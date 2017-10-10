package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.util.Log;
import android.webkit.WebView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by m.szymczyk on 2017-10-09.
 */

class Subjects implements Serializable {
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

    Subjects(MainActivity mainActivity) {
        Log.w("UWAGA", "konstruktor subjects");

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


        WebView browser = mainActivity.getTempWebView();
        browser.getSettings().setJavaScriptEnabled(true);
        browser.getSettings().setDomStorageEnabled(true);
        browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        browser.setWebViewClient(new MyWebViewClient(browser));
        browser.addJavascriptInterface(new GradesJavaScriptInterface(mainActivity), "GRADES_HTMLOUT");
        //browser.addJavascriptInterface(new MyWebViewClient.attendingJavaScriptInterface(), "ATTENDING_HTMLOUT");
        browser.loadUrl("https://uonetplus.vulcan.net.pl/lublin/LoginEndpoint.aspx");
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

    public int size() {
        return subjects.size();
    }
}
