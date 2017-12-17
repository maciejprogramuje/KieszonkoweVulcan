package commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Grade;

public class Grades {
    public static String[] getArrayAveragePropositionSem(String html, String subject) {
        String[] tempArr = new String[3];
        int index = 0;
        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));
        if (temp.contains("</span></td>")) {
            Matcher m = Pattern.compile("<td>[0123456789,-]+</td>").matcher(temp);
            while (m.find()) {
                tempArr[index] = m.group()
                        .replace("<td>", "")
                        .replace("</td>", "")
                        .replace("-", "");
                index++;
            }
        }
        return tempArr;
    }

    public static List<Grade> getArray(String html, String subject) {
        List<Grade> tempGrades = new ArrayList<>();
        String tempGrade = "";
        String tempDate = "";
        String tempText = "";
        String tempCode = "";
        String tempWeight = "";

        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));


        Matcher gradeMatcher = Pattern.compile("[1-6][\\+\\-]?</span>|np\\.|np|nb\\.|nb|NP\\.|NP|NB\\.|NB").matcher(temp);
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
                tempText = tempText.replaceAll("&" + "quot;", "'");
            }

            if (codeMatcher.find()) {
                tempCode = codeMatcher.group().substring(5);
                tempCode = tempCode.replace("<br/>", "");
            }

            if (weightMather.find()) {
                tempWeight = weightMather.group().substring(6);
                tempWeight = tempWeight.replace(",00<br/>", "");
            }

            //Log.w("UWAGA", "dodaję: " +tempGrade+", "+tempDate+", "+tempText+", "+tempCode+", "+tempWeight);
            tempGrades.add(new Grade(tempGrade, tempDate, tempText, tempCode, tempWeight));
        }

        return tempGrades;
    }
}
