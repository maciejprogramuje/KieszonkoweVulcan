package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.HtmlParsers.Grades;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Grade;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.DataFile;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.SubjectsInOriginOrder;

/**
 * Created by m.szymczyk on 2017-10-09.
 */

class GradesJavaScriptInterface {
    public static final String KIESZONKOWE_FILE = "kieszonkoweVulcanGrades.dat";
    private MainActivity mainActivity;
    private ArrayList<Subject> oldSubjectsArray;
    private ArrayList<Subject> newSubjectsArray;
    private Subjects oldSubjects;

    GradesJavaScriptInterface(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        //Log.w("UWAGA", "parsuję stronę...");
        int numOfSubjects = mainActivity.subjects.size();
        // read old data
        if (DataFile.isExists(mainActivity.getApplicationContext(), KIESZONKOWE_FILE)) {
            try {
                oldSubjects = DataFile.read(mainActivity.getApplicationContext(), KIESZONKOWE_FILE);
            } catch (IOException | ClassNotFoundException e) {
                Log.w("UWAGA", "problem z plikiem");
                oldSubjects = null;
            }
            if (oldSubjects != null) {
                oldSubjectsArray = SubjectsInOriginOrder.generate(oldSubjects);
            }
        }

        // generate new data
        for (int i = 0; i < numOfSubjects; i++) {
            set(html, i);
            newSubjectsArray = SubjectsInOriginOrder.generate(mainActivity.subjects);
        }

        //compare
        if (oldSubjects != null) {
            String toastMessage = "";

            for (int i = 0; i < numOfSubjects; i++) {
                int oldLength = oldSubjectsArray.get(i).getSubjectGrades().size();
                int newLength = newSubjectsArray.get(i).getSubjectGrades().size();

                //Log.w("UWAGA", "Sprawdzam nowości...");
                String message2 = "";
                if (newLength > oldLength) {
                    for (int j = 0; j < newLength - oldLength; j++) {
                        message2 = "NOWA OCENA:\n" +
                                newSubjectsArray.get(i).getSubjectName() +
                                ": " + newSubjectsArray.get(i).getSubjectGrades().get(oldLength + j).getmGrade() +
                                " (" + newSubjectsArray.get(i).getSubjectGrades().get(oldLength + j).getmDate() + ")" +
                                " , " + newSubjectsArray.get(i).getSubjectGrades().get(oldLength + j).getmCode() + " " +
                                newSubjectsArray.get(i).getSubjectGrades().get(oldLength + j).getmText();
                        Log.w("UWAGA", message2);
                        toastMessage += message2;
                    }
                } else {
                    //Log.w("UWAGA", "  - brak nowości!");
                }
            }

            if(toastMessage == "") {
                toastMessage = "Brak nowych ocen!";
            }

            Toast.makeText(mainActivity.getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
        }

        // write new data as old data
        DataFile.write(mainActivity.getApplicationContext(), mainActivity.subjects, KIESZONKOWE_FILE);

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.progressDialog.dismiss();

                if (mainActivity.getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof HelloFragment) {
                    mainActivity.navigationView.setCheckedItem(R.id.nav_news);
                    mainActivity.onNavigationItemSelected(mainActivity.navigationView.getMenu().findItem(R.id.nav_news));
                }
            }
        });
    }

    private void set(String htmlAsString, int subjectIndex) {
        // ustal nazwę przedmiotu
        String tempName = mainActivity.subjects.getName(subjectIndex);
        // wypełnij subjects danymi
        mainActivity.subjects.setGrades(subjectIndex, Grades.getArray(htmlAsString, tempName));
        mainActivity.subjects.setAverage(subjectIndex, Grades.getAverage(htmlAsString, tempName));
        mainActivity.subjects.setNewestDate(subjectIndex);
    }
}
