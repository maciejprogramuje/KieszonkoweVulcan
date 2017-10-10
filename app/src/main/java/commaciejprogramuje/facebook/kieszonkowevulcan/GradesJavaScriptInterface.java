package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity.RESULTS_KEY;
import static commaciejprogramuje.facebook.kieszonkowevulcan.NavMenuButtonsTitle.*;

/**
 * Created by m.szymczyk on 2017-10-09.
 */

public class GradesJavaScriptInterface {
    MainActivity mainActivity;

    public GradesJavaScriptInterface(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        for(int i = 0; i < mainActivity.subjects.size(); i++) {
            set(html, i);
        }
    }

    private void set(String htmlAsString, int subjectIndex) {
        // ustal nazwę przedmiotu
        String tempName = mainActivity.subjects.getName(subjectIndex);;
        // wypełnij subjects danymi
        mainActivity.subjects.setGrades(subjectIndex, getGrades(htmlAsString, tempName));
        mainActivity.subjects.setAverage(subjectIndex, getAverageGrades(htmlAsString, tempName));
    }

    private String getGrades(String html, String subject) {
        StringBuilder gradesStringBuilder = new StringBuilder("");
        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));
        Matcher m = Pattern.compile("[1-6]{1}</span>").matcher(temp);
        while (m.find()) {
            gradesStringBuilder.append(m.group().substring(0, 1)).append(", ");
        }
        return gradesStringBuilder.toString();
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
