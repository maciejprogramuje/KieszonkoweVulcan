package commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Grade;

public class Grades {
    public static String[] getArrayAveragePropositionSem(String html, String subject) {
        String[] tempArr = {"", "", ""};
        int index = 0;
        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));

        //Log.w("UWAGA", "temp: " +temp);

        if (temp.contains("</span></td>") || temp.contains("Brak ocen</td>")) {
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

        if (!temp.contains("Brak ocen")) {
            Matcher spanMatcher = Pattern.compile("<span class=\"ocenaCzastkowa masterTooltip\"(.*?)</span>").matcher(temp);
            Matcher gradeMatcher = Pattern.compile("style=\"color:#[0-9A-Fa-f]{6};\">(.*?)</span>").matcher(temp);
            Matcher dateMatcher = Pattern.compile("Data: \\d{2}\\.\\d{2}\\.\\d{4}").matcher(temp);
            Matcher textMatcher = Pattern.compile("Opis:(.*?)<br/>").matcher(temp);
            Matcher codeMatcher = Pattern.compile("Kod:(.*?)<br/>").matcher(temp);
            Matcher weightMather = Pattern.compile("Waga:(.*?)<br/>").matcher(temp);

            while (spanMatcher.find()) {
                if (gradeMatcher.find()) {
                    String tGr = gradeMatcher.group();
                    tempGrade = tGr.substring(tGr.indexOf(";\">") + 3, tGr.indexOf("</span>"));
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
                //Log.w("UWAGA", "dodaję: " + tempGrade + ", " + tempDate + ", " + tempText + ", " + tempCode + ", " + tempWeight);
                tempGrades.add(new Grade(tempGrade, tempDate, tempText, tempCode, tempWeight));
            }
        }
        return tempGrades;
    }
}
