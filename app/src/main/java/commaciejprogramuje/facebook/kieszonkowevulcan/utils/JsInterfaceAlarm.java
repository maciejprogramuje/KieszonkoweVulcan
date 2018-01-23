package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import java.io.IOException;
import java.util.ArrayList;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers.Grades;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE_SEM_1;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE_SEM_2;


public class JsInterfaceAlarm {
    private ArrayList<Subject> oldSubjectsArray;
    private ArrayList<Subject> newSubjectsArray;
    private Subjects oldSubjects;
    private Subjects newSubjects;
    private Context context;
    private boolean firstSem;

    public JsInterfaceAlarm(Context context, boolean firstSem) {
        this.context = context;
        this.firstSem = firstSem;
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
        if(firstSem) {
            if (DataFile.isExists(context, KIESZONKOWE_FILE_SEM_1)) {
                try {
                    oldSubjects = DataFile.read(context, KIESZONKOWE_FILE_SEM_1);
                    oldSubjectsArray = DataFile.originOrder(oldSubjects);
                } catch (IOException | ClassNotFoundException e) {
                    oldSubjects = null;
                }
            }
        } else {
            if (DataFile.isExists(context, KIESZONKOWE_FILE_SEM_2)) {
                try {
                    oldSubjects = DataFile.read(context, KIESZONKOWE_FILE_SEM_2);
                    oldSubjectsArray = DataFile.originOrder(oldSubjects);
                } catch (IOException | ClassNotFoundException e) {
                    oldSubjects = null;
                }
            }
        }

        // originOrder new data
        for (int i = 0; i < numOfSubjects; i++) {
            // ustal nazwę przedmiotu
            String tempName = newSubjects.getName(i);
            // wypełnij subjects danymi
            newSubjects.setGrades(i, Grades.getArray(html, tempName));
            newSubjects.setAverage(i, Grades.getArrayAveragePropositionSem(html, tempName)[0]);
            newSubjects.setProposition(i, Grades.getArrayAveragePropositionSem(html, tempName)[1]);
            newSubjects.setSem(i, Grades.getArrayAveragePropositionSem(html, tempName)[2]);
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
                        MultiUtils.showNotification(context, message);
                    }
                }
            }
            // there is not new grade
            if(message.equals("")) {
                Log.w("UWAGA", "Brak nowych ocen");


                ////////////////// TEST ///////////////////////
                if(firstSem) {
                    MultiUtils.showNotification(context, "Brak nowych ocen, sem 1");
                } else {
                    MultiUtils.showNotification(context, "Brak nowych ocen, sem 2");
                }
            }
        }

        // write new data as old data
        Log.w("UWAGA", "nadpisuję plik");
        if(firstSem) {
            DataFile.write(context, newSubjects, KIESZONKOWE_FILE_SEM_1);
        } else {
            DataFile.write(context, newSubjects, KIESZONKOWE_FILE_SEM_2);
        }

        onAlarmInteraction.onAlarmInteraction(true);
    }

    public interface OnAlarmInteractionListener {
        void onAlarmInteraction(boolean fileFlag);
    }
}
