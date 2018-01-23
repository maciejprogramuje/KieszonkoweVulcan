package commaciejprogramuje.facebook.kieszonkowevulcan.utils;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Subjects;
import commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers.Grades;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE_SEM_1;
import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.KIESZONKOWE_FILE_SEM_2;

public class JsInterfaceGrades {
    private ArrayList<Subject> oldSubjectsArray;
    private ArrayList<Subject> newSubjectsArray;
    private Subjects oldSubjects;
    private Subjects newSubjects;
    private Context context;

    public JsInterfaceGrades(Context context) {
        this.context = context;
        newSubjects = new Subjects();
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        Log.w("UWAGA", "parsuję stronę...");

        OnGradesMainInteractionListener onFileSavedInteraction;

        if (context instanceof OnGradesMainInteractionListener) {
            onFileSavedInteraction = (OnGradesMainInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnGradesMainInteractionListener");
        }


        int numOfSubjects = newSubjects.size();
        // read old data
        if(MainActivity.isFirstSemestr()) {
            if (DataFile.isExists(context, KIESZONKOWE_FILE_SEM_1)) {
                try {
                    Log.w("UWAGA", "czytam plik");
                    oldSubjects = DataFile.read(context, KIESZONKOWE_FILE_SEM_1);
                    oldSubjectsArray = DataFile.originOrder(oldSubjects);
                } catch (IOException | ClassNotFoundException e) {
                    Log.w("UWAGA", "problem z plikiem");
                    oldSubjects = null;
                }
            }
        } else {
            if (DataFile.isExists(context, KIESZONKOWE_FILE_SEM_2)) {
                try {
                    Log.w("UWAGA", "czytam plik");
                    oldSubjects = DataFile.read(context, KIESZONKOWE_FILE_SEM_2);
                    oldSubjectsArray = DataFile.originOrder(oldSubjects);
                } catch (IOException | ClassNotFoundException e) {
                    Log.w("UWAGA", "problem z plikiem");
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
            //Log.w("UWAGA", "dodaję "+tempName+", avg="+Grades.getAverage(html, tempName)+", oceny="+Grades.getArray(html, tempName));
            newSubjectsArray = DataFile.originOrder(newSubjects);
        }

        //compare
        if (oldSubjects != null) {
            String message = "";
            for (int i = 0; i < numOfSubjects; i++) {
                int oldLength = oldSubjectsArray.get(i).getSubjectGrades().size();
                int newLength = newSubjectsArray.get(i).getSubjectGrades().size();

                //Log.w("UWAGA", "Sprawdzam nowości...");
                if (newLength > oldLength) {
                    // there is new grade
                    for (int j = 0; j < newLength - oldLength; j++) {
                        message = newSubjectsArray.get(i).getSubjectName() + ": " + newSubjectsArray.get(i).getSubjectGrades().get(oldLength + j).getmGrade();
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                    }
                }
            }
            // there is not new grade
            if (message.equals("")) {
                Toast.makeText(context, "Brak nowych ocen!", Toast.LENGTH_LONG).show();
            }
        }

        // write new data as old data
        Log.w("UWAGA", "nadpisuję plik");
        if(MainActivity.isFirstSemestr()) {
            DataFile.write(context, newSubjects, KIESZONKOWE_FILE_SEM_1);
        } else {
            DataFile.write(context, newSubjects, KIESZONKOWE_FILE_SEM_2);
        }

        onFileSavedInteraction.onGradesMainInteraction(true);
    }

    public interface OnGradesMainInteractionListener {
        void onGradesMainInteraction(boolean fileFlag);
    }
}
