package commaciejprogramuje.facebook.kieszonkowevulcan.html_parsers;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.gim_16.Teacher;

public class Teachers {

    private String getDate() {
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
        StringBuilder substitute;

        // iterate by teacher section
        Matcher sectionMatcher = Pattern.compile("colspan=\"3\"(.|\\n)*?(st1\"|</body>)").matcher(getPageBody());
        while (sectionMatcher.find()) {
            String sectionElement = sectionMatcher.group();

            if (!sectionElement.contains("dyżury")) {
                // gdy w colspan="3" align="LEFT" bgcolor="#FFDFBF"> Anna Celińska </td> znajdują się info o dniu (tu nie ma!)
                //Matcher nameMatcher = Pattern.compile(">(.*?)\\d{2,4}[\\.\\/-]\\d{2}[\\.\\/-]\\d{2,4}(.*?)</td>").matcher(sectionElement);
                Matcher nameMatcher = Pattern.compile("colspan=\"3\" align=\"LEFT\" bgcolor=\"#[\\w]{6}\">(.*?)</td>").matcher(sectionElement);
                if (nameMatcher.find()) {
                    String nameElement = nameMatcher.group();
                    name = nameElement.substring(nameElement.indexOf(">") + 2, nameElement.indexOf("<") - 1);
                }

                Matcher oneRowMatcher = Pattern.compile("<td nowrap class=\"st(\\d){1,2}\" align=\"LEFT\"(.*?)</td>").matcher(sectionElement);
                substitute = new StringBuilder();
                int index = 0;

                while (oneRowMatcher.find()) {
                    String oneRow = oneRowMatcher.group();
                    oneRow = oneRow.substring(oneRow.indexOf("\">") + 2, oneRow.indexOf("</td>"))
                            .replace("&" + "nbsp;", "")
                            .trim();

                    //Log.w("UWAGA", oneRow);

                    if (!oneRow.contains("lekcja") && !oneRow.contains("opis") && !oneRow.contains("zastępca") && !oneRow.contains("po lekcji") && !oneRow.contains("miejsce") && !oneRow.contains("dyżury")
                            && !oneRow.contains("poniedziałek") && !oneRow.contains("wtorek") && !oneRow.contains("środa") && !oneRow.contains("czwartek") && !oneRow.contains("piątek")) {
                        if (index == 0 && !oneRow.equals("")) {
                            substitute.append(oneRow);
                        } else if (index == 1 && !oneRow.equals("")) {
                            substitute.append(" -> ").append(oneRow);
                        } else if (index == 2 && !oneRow.equals("")) {
                            substitute.append(" -> ").append(oneRow).append("\n");
                        } else if (index == 2 && oneRow.equals("")) {
                            substitute.append("\n");
                        }
                        index++;
                        if (index == 3) {
                            index = 0;
                        }
                    }
                }
                while (substitute.toString().endsWith("\n")) {
                    substitute = new StringBuilder(substitute.substring(0, substitute.length() - 1));
                }

                if (!name.equals("")) {
                    teacherArray.add(new Teacher(name, substitute.toString()));
                }
            }
        }

        return teacherArray;
    }

    private static String getPageBody() {
        String tBody = null;

        while (tBody == null) {
            try {
                tBody = Jsoup.parse(new URL("http://zastepstwa.g16-lublin.eu/"), 10000).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tBody;
    }
}
