package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.SchoolUtils.Grade;
import commaciejprogramuje.facebook.kieszonkowevulcan.SchoolUtils.Subject;
import commaciejprogramuje.facebook.kieszonkowevulcan.SchoolUtils.Subjects;
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
        Log.w("UWAGA", "parsuję stronę...");
        int numOfSubjects = mainActivity.subjects.size();
        // read old data
        if (fileExists(mainActivity.getApplicationContext(), KIESZONKOWE_FILE)) {
            Log.w("UWAGA", "plik istnieje " + KIESZONKOWE_FILE);
            oldSubjects = readSubjectsFromFile(mainActivity.getApplicationContext(), KIESZONKOWE_FILE);
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
            Log.w("UWAGA", "porównanie");

            String toastMessage = "";

            for (int i = 0; i < numOfSubjects; i++) {
                int oldLength = oldSubjectsArray.get(i).getSubjectGrades().size();
                int newLength = newSubjectsArray.get(i).getSubjectGrades().size();

                Log.w("UWAGA", "Sprawdzam zmiany (" + oldSubjectsArray.get(i).getSubjectName() + ")");
                String message1 = "";
                for (int j = 0; j < oldLength; j++) {
                    if (isDifference(oldSubjectsArray.get(i).getSubjectGrades().get(j), newSubjectsArray.get(i).getSubjectGrades().get(j))) {
                        message1 = message1 + "ZMIANA OCENY:\n" +
                                "Stara ocena: " + oldSubjectsArray.get(i).getSubjectName() + //przedmiot
                                ": " + oldSubjectsArray.get(i).getSubjectGrades().get(j).getmGrade() + //ocena
                                " (" + oldSubjectsArray.get(i).getSubjectGrades().get(j).getmDate() + ")" + //data
                                " , " + oldSubjectsArray.get(i).getSubjectGrades().get(j).getmCode() + "\n" + // kod
                                "Nowa ocena: " + mainActivity.subjects.getName(i) + //przedmiot
                                ": " + newSubjectsArray.get(i).getSubjectGrades().get(j).getmGrade() + //ocena
                                " (" + newSubjectsArray.get(i).getSubjectGrades().get(j).getmDate() + ")" + //data
                                " , " + newSubjectsArray.get(i).getSubjectGrades().get(j).getmCode() + "\n";// kod
                        Log.w("UWAGA", message1);
                        toastMessage += message1;
                    } else {
                        Log.w("UWAGA", "  - brak zmian!");
                    }
                }

                Log.w("UWAGA", "Sprawdzam nowości...");
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
                    Log.w("UWAGA", "  - brak nowości!");
                }
            }

            if(toastMessage == "") {
                toastMessage = "Brak nowych ocen!";
            }

            Toast.makeText(mainActivity.getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
            Log.w("UWAGA", "toast: " + toastMessage);
            Log.w("UWAGA", "porównanie - koniec");
        }

        // write new data as old data
        writeSubjectsToFile(mainActivity.getApplicationContext(), mainActivity.subjects, KIESZONKOWE_FILE);

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

    private boolean isDifference(Grade oldGrade, Grade newGrade) {
        if(!oldGrade.getmGrade().equals(newGrade.getmGrade())) {
            Log.w("UWAGA", "warunek 1");
            return true;
        } else if(!oldGrade.getmDate().equals(newGrade.getmDate())) {
            Log.w("UWAGA", "warunek 2");
            return true;
        } else if(!oldGrade.getmCode().equals(newGrade.getmCode())) {
            Log.w("UWAGA", "warunek 3");
            return true;
        } else if(!oldGrade.getmText().equals(newGrade.getmText())) {
            Log.w("UWAGA", "warunek 4");
            return true;
        } else if(!oldGrade.getmWeight().equals(newGrade.getmWeight())) {
            Log.w("UWAGA", "warunek 5");
            return true;
        } else {
            Log.w("UWAGA", "warunek F");
            return false;
        }
    }

    private static boolean fileExists(Context context, String filename) {
        File file = context.getFileStreamPath(filename);
        return !(file == null || !file.exists());
    }

    private Subjects readSubjectsFromFile(Context context, String filename) {
        ObjectInputStream objectIn = null;
        Subjects rSubjects = null;
        try {
            FileInputStream fileIn = context.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            rSubjects = (Subjects) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            Log.w("UWAGA", "problem z plikiem");
            oldSubjects = null;
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return rSubjects;
    }

    private static void writeSubjectsToFile(Context context, Subjects mSubjects, String filename) {
        ObjectOutputStream objectOut = null;
        try {
            FileOutputStream fileOut = context.openFileOutput(filename, Activity.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(mSubjects);
            fileOut.getFD().sync();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void set(String htmlAsString, int subjectIndex) {
        // ustal nazwę przedmiotu
        String tempName = mainActivity.subjects.getName(subjectIndex);
        ;
        // wypełnij subjects danymi
        mainActivity.subjects.setGrades(subjectIndex, getGradesFromPage(htmlAsString, tempName));
        mainActivity.subjects.setAverage(subjectIndex, getAverageGradesFromPage(htmlAsString, tempName));
        mainActivity.subjects.setNewestDate(subjectIndex);
    }

    private List<Grade> getGradesFromPage(String html, String subject) {
        List<Grade> tempGrades = new ArrayList<>();
        String tempGrade = "";
        String tempDate = "";
        String tempText = "";
        String tempCode = "";
        String tempWeight = "";

        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));

        Matcher gradeMatcher = Pattern.compile("[1-6]{1}[\\+]*[\\-]*</span>").matcher(temp);
        Matcher dateMatcher = Pattern.compile("Data: \\d{2}\\.\\d{2}\\.\\d{4}").matcher(temp);
        Matcher textMatcher = Pattern.compile("Opis:(.*?)<br/>").matcher(temp);
        Matcher codeMatcher = Pattern.compile("Kod:(.*?)<br/>").matcher(temp);
        Matcher weightMather = Pattern.compile("Waga:(.*?)<br/>").matcher(temp);

        while (gradeMatcher.find()) {
            String tempGradeString = gradeMatcher.group().substring(0, 2);
            if (tempGradeString.contains("<")) {
                tempGrade = gradeMatcher.group().substring(0, 1);
            } else {
                tempGrade = tempGradeString;
            }

            if (dateMatcher.find()) {
                tempDate = dateMatcher.group().substring(6);
            }

            if (textMatcher.find()) {
                tempText = textMatcher.group().substring(6);
                tempText = tempText.replace("<br/>", "");
                tempText = tempText.replaceAll("&quot;", "'");
            }

            if (codeMatcher.find()) {
                tempCode = codeMatcher.group().substring(5);
                tempCode = tempCode.replace("<br/>", "");
            }

            if (weightMather.find()) {
                tempWeight = weightMather.group().substring(6);
                tempWeight = tempWeight.replace(",00<br/>", "");
            }

            tempGrades.add(new Grade(tempGrade, tempDate, tempText, tempCode, tempWeight));
        }
        return tempGrades;
    }

    private String getAverageGradesFromPage(String html, String subject) {
        StringBuilder averageGradesStringBuilder = new StringBuilder("");
        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));
        if (temp.contains("</span></td>")) {
            Matcher m = Pattern.compile("[1-6]{1}[,]?[0-9]?[0-9]?</td>").matcher(temp);
            while (m.find()) {
                averageGradesStringBuilder.append(m.group().replace("</td>", ""));
            }
        }
        return averageGradesStringBuilder.toString();
    }
}
