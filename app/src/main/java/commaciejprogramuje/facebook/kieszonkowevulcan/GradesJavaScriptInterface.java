package commaciejprogramuje.facebook.kieszonkowevulcan;

import android.webkit.JavascriptInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        System.out.println("=================================================================================");
        System.out.println("Język polski: " + getGrades(html, "Język polski"));
        System.out.println("Język angielski: " + getGrades(html, "Język angielski"));
        System.out.println("Język niemiecki: " + getGrades(html, "Język niemiecki"));
        System.out.println("Muzyka: " + getGrades(html, "Muzyka"));
        System.out.println("Historia: " + getGrades(html, "Historia"));
        System.out.println("Wiedza o społeczeństwie: " + getGrades(html, "Wiedza o społeczeństwie"));
        System.out.println("Geografia: " + getGrades(html, "Geografia"));
        System.out.println("Biologia: " + getGrades(html, "Biologia"));
        System.out.println("Chemia: " + getGrades(html, "Chemia"));
        System.out.println("Fizyka: " + getGrades(html, "Fizyka"));
        System.out.println("Matematyka: " + getGrades(html, "Matematyka"));
        System.out.println("Informatyka: " + getGrades(html, "Informatyka"));
        System.out.println("Wychowanie fizyczne: " + getGrades(html, ""));
        System.out.println("Edukacja dla bezpieczeństwa: " + getGrades(html, "Edukacja dla bezpieczeństwa"));
        System.out.println("Zajęcia techniczne: " + getGrades(html, "Zajęcia techniczne"));
        System.out.println("Wychowanie do życia w rodzinie: " + getGrades(html, "Wychowanie do życia w rodzinie"));
        System.out.println("Etyka: " + getGrades(html, "Etyka"));
        System.out.println("Zajęcia z wychowawcą: " + getGrades(html, "Zajęcia z wychowawcą"));

        System.out.println("=================================================================================");
        System.out.println("Język polski (średnia): " + getAverageGrades(html, "Język polski"));
        System.out.println("Język angielski (średnia): " + getAverageGrades(html, "Język angielski"));
        System.out.println("Język niemiecki (średnia): " + getAverageGrades(html, "Język niemiecki"));
        System.out.println("Muzyka (średnia): " + getAverageGrades(html, "Muzyka"));
        System.out.println("Historia (średnia): " + getAverageGrades(html, "Historia"));
        System.out.println("Wiedza o społeczeństwie (średnia): " + getAverageGrades(html, "Wiedza o społeczeństwie"));
        System.out.println("Geografia (średnia): " + getAverageGrades(html, "Geografia"));
        System.out.println("Biologia (średnia): " + getAverageGrades(html, "Biologia"));
        System.out.println("Chemia (średnia): " + getAverageGrades(html, "Chemia"));
        System.out.println("Fizyka (średnia): " + getAverageGrades(html, "Fizyka"));
        System.out.println("Matematyka (średnia): " + getAverageGrades(html, "Matematyka"));
        System.out.println("Informatyka (średnia): " + getAverageGrades(html, "Informatyka"));
        System.out.println("Wychowanie fizyczne (średnia): " + getAverageGrades(html, "Wychowanie fizyczne"));
        System.out.println("Edukacja dla bezpieczeństwa (średnia): " + getAverageGrades(html, "Edukacja dla bezpieczeństwa"));
        System.out.println("Zajęcia techniczne (średnia): " + getAverageGrades(html, "Zajęcia techniczne"));
        System.out.println("Wychowanie do życia w rodzinie (średnia): " + getAverageGrades(html, "Wychowanie do życia w rodzinie"));
        System.out.println("Etyka" + getAverageGrades(html, "Etyka"));
        System.out.println("Zajęcia z wychowawcą (średnia): " + getAverageGrades(html, "Zajęcia z wychowawcą"));
        System.out.println("=================================================================================");
    }

    private List<String> getGrades(String html, String subject) {
        List<String> gradesArray = new ArrayList<>();
        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));
        Matcher m = Pattern.compile("[1-6]{1}</span>").matcher(temp);
        while (m.find()) {
            gradesArray.add(m.group().substring(0, 1));
        }
        return gradesArray;
    }

    private List<String> getAverageGrades(String html, String subject) {
        List<String> averageGradesArray = new ArrayList<>();
        String temp = html.substring(html.indexOf(subject));
        temp = temp.substring(0, temp.indexOf("</tr>"));
        if (temp.contains("</span></td>")) {
            Matcher m = Pattern.compile("[1-6]{1}[,]?[0-9]?[0-9]?</td>").matcher(temp);
            while (m.find()) {
                averageGradesArray.add(m.group().replace("</td>", ""));
            }
        } else {
            averageGradesArray.add("-");
        }
        return averageGradesArray;
    }
}
