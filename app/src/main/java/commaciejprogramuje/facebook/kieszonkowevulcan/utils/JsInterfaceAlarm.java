package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.IOException;
import java.util.ArrayList;

import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers.Grades;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE;

/**
 * Created by 5742ZGPC on 2017-10-31.
 */

public class JsInterfaceAlarm {
    private ArrayList<Subject> oldSubjectsArray;
    private ArrayList<Subject> newSubjectsArray;
    private Subjects oldSubjects;
    private Subjects newSubjects;
    private Context context;

    public JsInterfaceAlarm(Context context) {
        this.context = context;
        newSubjects = new Subjects();
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        Log.w("UWAGA", "ALARM -> parsuję stronę...");

        OnAlarmInteractionListener onAlarmInteraction;

        if (context instanceof OnAlarmInteractionListener) {
            onAlarmInteraction = (OnAlarmInteractionListener) context;
        } else {
            throw new RuntimeException(context + " must implement OnAlarmInteractionListener");
        }

        int numOfSubjects = newSubjects.size();
        // read old data
        if (DataFile.isExists(context, KIESZONKOWE_FILE)) {
            try {
                oldSubjects = DataFile.read(context, KIESZONKOWE_FILE);
                oldSubjectsArray = DataFile.originOrder(oldSubjects);
            } catch (IOException | ClassNotFoundException e) {
                oldSubjects = null;
            }
        }

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

        //compare
        if (oldSubjects != null) {
            String message = "";
            for (int i = 0; i < numOfSubjects; i++) {
                int oldLength = oldSubjectsArray.get(i).getSubjectGrades().size();
                int newLength = newSubjectsArray.get(i).getSubjectGrades().size();

                if (newLength > oldLength) {
                    for (int j = 0; j < newLength - oldLength; j++) {
                        message = newSubjectsArray.get(i).getSubjectName() + ": " + newSubjectsArray.get(i).getSubjectGrades().get(oldLength + j).getmGrade() + "\n";
                        NewGradeNotification.show(context, message);
                    }
                }
            }
            // there is not new grade
            if(message.equals("")) {
                //NewGradeNotification.show(context, "Brak nowych ocen");
            }
        }

        // write new data as old data
        Log.w("UWAGA", "nadpisuję plik");
        DataFile.write(context, newSubjects, KIESZONKOWE_FILE);

        onAlarmInteraction.onAlarmInteraction(true);
    }

    public interface OnAlarmInteractionListener {
        void onAlarmInteraction(boolean fileFlag);
    }
}
