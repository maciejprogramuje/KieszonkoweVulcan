package commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Teacher;

/**
 * Created by m.szymczyk on 2017-10-24.
 */

public class Teachers {
    public static String getDate() {
        try {
            Document document = Jsoup.connect("http://zastepstwa.g16-lublin.eu/").get();
            String temp = document.body().toString();
            return temp.substring(temp.indexOf("<nobr>") + 32, temp.indexOf("</nobr>"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ArrayList<Teacher> getArray() {
        ArrayList<Teacher> teacherArray = new ArrayList<>();
        String name = "";
        String substitute = "";

        try {
            Document document = Jsoup.connect("http://zastepstwa.g16-lublin.eu/").get();
            String temp = document.body().toString();

            // iterate by teacher section
            Matcher sectionMatcher = Pattern.compile("colspan=\"3\"(.|\\n)*?(st1\"|</body>)").matcher(temp);
            while (sectionMatcher.find()) {
                String sectionElement = sectionMatcher.group();

                if (!sectionElement.contains("dyżury")) {
                    Matcher nameMatcher = Pattern.compile(">(.*?)\\d{2,4}[\\.\\/-]\\d{2}[\\.\\/-]\\d{2,4}(.*?)</td>").matcher(sectionElement);
                    if (nameMatcher.find()) {
                        String nameElement = nameMatcher.group();
                        name = nameElement.substring(nameElement.indexOf(">") + 2, nameElement.indexOf("<") - 1);
                    }

                    Matcher oneRowMatcher = Pattern.compile("\">(.*?)</td>").matcher(sectionElement);
                    substitute = "";
                    int index = 0;

                    while (oneRowMatcher.find()) {
                        String oneRow = oneRowMatcher.group().replace("\"> ", "").replace(" </td>", "").replace("&" + "nbsp;", "");

                        if (!oneRow.contains("lekcja") && !oneRow.contains("opis") && !oneRow.contains("zastępca") && !oneRow.contains("po lekcji") && !oneRow.contains("miejsce") && !oneRow.contains("dyżury")
                                && !oneRow.contains("poniedziałek") && !oneRow.contains("wtorek") && !oneRow.contains("środa") && !oneRow.contains("czwartek") && !oneRow.contains("piątek")) {
                            if (index == 0 && !oneRow.equals("")) {
                                substitute = substitute + oneRow;
                            } else if (index == 1 && !oneRow.equals("")) {
                                substitute = substitute + " -> " + oneRow;
                            } else if (index == 2 && !oneRow.equals("")) {
                                substitute = substitute + " -> " + oneRow + "\n";
                            } else if (index == 2 && oneRow.equals("")) {
                                substitute = substitute + "\n";
                            }
                            index++;
                            if (index == 3) {
                                index = 0;
                            }
                        }
                    }
                    while (substitute.endsWith("\n")) {
                        substitute = substitute.substring(0, substitute.length() - 1);
                    }

                    if (name != "") {
                        teacherArray.add(new Teacher(name, substitute));
                    }
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return teacherArray;
    }
}
