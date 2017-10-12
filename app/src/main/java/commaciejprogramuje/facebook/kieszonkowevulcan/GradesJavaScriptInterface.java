package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by m.szymczyk on 2017-10-09.
 */

public class GradesJavaScriptInterface {
    private MainActivity mainActivity;

    public GradesJavaScriptInterface(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        for(int i = 0; i < mainActivity.subjects.size(); i++) {
            set(html, i);
        }

        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.progressDialog.dismiss();
            }
        });
    }

    private void set(String htmlAsString, int subjectIndex) {
        // ustal nazwę przedmiotu
        String tempName = mainActivity.subjects.getName(subjectIndex);;
        // wypełnij subjects danymi
        mainActivity.subjects.setGrades(subjectIndex, getGrades(htmlAsString, tempName));
        mainActivity.subjects.setAverage(subjectIndex, getAverageGrades(htmlAsString, tempName));
    }

    private List<Grade> getGrades(String html, String subject) {
        List<Grade> tempGrades = new ArrayList<>();
        String tempGrade = "";
        String tempDate = "";
        String tempText = "";

        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));

        Matcher gradeMatcher = Pattern.compile("[1-6]{1}</span>").matcher(temp);
        Matcher dateMatcher = Pattern.compile("Data: \\d{2}\\.\\d{2}\\.\\d{4}").matcher(temp);
        Matcher textMatcher = Pattern.compile("Opis:(.*?)<br/>").matcher(temp);

        while (gradeMatcher.find()) {
            tempGrade = gradeMatcher.group().substring(0, 1);

            if(dateMatcher.find()) {
                tempDate = dateMatcher.group().substring(6);
            }

            if(textMatcher.find()) {
                tempText = textMatcher.group().substring(6);
                tempText = tempText.replace("<br/>", "");
                tempText = tempText.replaceAll("&quot;", "'");
            }
            tempGrades.add(new Grade(tempGrade, tempDate, tempText));
        }
        return tempGrades;
    }

    private String getAverageGrades(String html, String subject) {
        StringBuilder averageGradesStringBuilder = new StringBuilder("");
        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));
        if (temp.contains("</span></td>")) {
            Matcher m = Pattern.compile("[1-6]{1}[,]?[0-9]?[0-9]?</td>").matcher(temp);
            while (m.find()) {
                averageGradesStringBuilder.append(m.group().replace("</td>", ""));
            }
        } else {
            averageGradesStringBuilder.append("-");
        }
        return averageGradesStringBuilder.toString();
    }
}
