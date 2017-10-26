package commaciejprogramuje.facebook.kieszonkowevulcan.Utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.GradesFromPageActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.HtmlParsers.Grades;
import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Grade;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.School.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.DataFile;
import commaciejprogramuje.facebook.kieszonkowevulcan.Utils.NewGradeNotification;

/**
 * Created by m.szymczyk on 2017-10-09.
 */

public class GradesJavaScriptInterface {
    public static final String KIESZONKOWE_FILE = "kieszonkoweVulcanGrades.dat";
    private ArrayList<Subject> oldSubjectsArray;
    private ArrayList<Subject> newSubjectsArray;
    private Subjects oldSubjects;
    private Subjects newSubjects;
    private Context context;

    public GradesJavaScriptInterface(Context context) {
        this.context = context;
        newSubjects = new Subjects();
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        Log.w("UWAGA", "parsuję stronę...");
        int numOfSubjects = 16;
        // read old data
        if (DataFile.isExists(context, KIESZONKOWE_FILE)) {
            try {
                oldSubjects = DataFile.read(context, KIESZONKOWE_FILE);
            } catch (IOException | ClassNotFoundException e) {
                Log.w("UWAGA", "problem z plikiem");
                oldSubjects = null;
            }
            if (oldSubjects != null) {
                oldSubjectsArray = DataFile.originOrder(oldSubjects);
            }
        }

        Log.w("UWAGA", "OLD: " + oldSubjectsArray);

        // originOrder new data
        for (int i = 0; i < numOfSubjects; i++) {
            // ustal nazwę przedmiotu
            String tempName = newSubjects.getName(i);
            // wypełnij subjects danymi
            newSubjects.setGrades(i, Grades.getArray(html, tempName));
            newSubjects.setAverage(i, Grades.getAverage(html, tempName));
            newSubjects.setNewestDate(i);
            newSubjectsArray = DataFile.originOrder(newSubjects);
        }

        Log.w("UWAGA", "NEW: " + newSubjectsArray);

        //compare
        if (oldSubjects != null) {
            String message = "";
            for (int i = 0; i < numOfSubjects; i++) {
                int oldLength = oldSubjectsArray.get(i).getSubjectGrades().size();
                int newLength = newSubjectsArray.get(i).getSubjectGrades().size();

                //Log.w("UWAGA", "Sprawdzam nowości...");
                if (newLength > oldLength) {
                    for (int j = 0; j < newLength - oldLength; j++) {
                        message = newSubjectsArray.get(i).getSubjectName() + ": " + newSubjectsArray.get(i).getSubjectGrades().get(oldLength + j).getmGrade() + "\n";
                        Log.w("UWAGA", message);
                        NewGradeNotification.show(context, message);
                    }
                }
            }
            if(message.equals("")) {
                Log.w("UWAGA", "  - brak nowości!");
                Toast.makeText(context, "Brak nowych ocen!", Toast.LENGTH_LONG).show(); // na czas testów
            }
        }

        // write new data as old data
        DataFile.write(context, newSubjects, KIESZONKOWE_FILE);

        /*mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.progressDialog.dismiss();

                if (mainActivity.getSupportFragmentManager().findFragmentById(R.id.main_container) instanceof HelloFragment) {
                    mainActivity.navigationView.setCheckedItem(R.id.nav_news);
                    mainActivity.onNavigationItemSelected(mainActivity.navigationView.getMenu().findItem(R.id.nav_news));
                }
            }
        });*/
    }
}
