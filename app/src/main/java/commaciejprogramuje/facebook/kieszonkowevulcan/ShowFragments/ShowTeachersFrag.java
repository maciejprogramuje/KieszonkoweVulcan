package commaciejprogramuje.facebook.kieszonkowevulcan.ShowFragments;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import commaciejprogramuje.facebook.kieszonkowevulcan.MainActivity;
import commaciejprogramuje.facebook.kieszonkowevulcan.SchoolUtils.Teacher;
import commaciejprogramuje.facebook.kieszonkowevulcan.TeacherFragment;

/**
 * Created by m.szymczyk on 2017-10-16.
 */

public class ShowTeachersFrag {
    private final MainActivity mainActivity;
    private String substituteDate = "test test";

    public ShowTeachersFrag(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void show() {
        if (mainActivity.checkInternetConn.checkInternetConnection(mainActivity)) {
            TeacherFragment teacherFragment = TeacherFragment.newInstance(getTeacherArray(), substituteDate);
            mainActivity.replaceFrag.replace(mainActivity, teacherFragment);
        } else {
            mainActivity.noInternetReaction.noInternetReaction(mainActivity);
        }
    }

    private ArrayList<Teacher> getTeacherArray() {
        ArrayList<Teacher> teacherArray = new ArrayList<>();
        String name = "name";
        String substitute = "substitute";
        String onDuty = "on duty";

        try {
            Document document = Jsoup.connect("http://zastepstwa.g16-lublin.eu/").get();
            String temp = document.body().toString();

            //System.out.println(temp);

            // set date
            Matcher dateMatcher = Pattern.compile("Zastępstwa(.|\\n)*?\\d{2}\\.\\d{2}\\.\\d{4}").matcher(temp);
            while (dateMatcher.find()) {
                String dateElement = dateMatcher.group();
                dateElement = temp.substring(temp.indexOf("<nobr>") + 32, temp.indexOf("</nobr>"));
                substituteDate = dateElement;
            }

            // iterate by teacher section
            Matcher sectionMatcher = Pattern.compile("st1\"(.*)\\d{2}.\\d{2}\\.\\d{4}(.|\\n)*?(st19|</body>)").matcher(temp);
            while (sectionMatcher.find()) {
                String sectionElement = sectionMatcher.group();

                //<td nowrap class="st1" colspan="3" align="LEFT" bgcolor="#FFDFBF"> Izabela Sałuch / 24.10.2017 wtorek </td>
                Matcher nameMatcher = Pattern.compile("st1\"(.*?)\\d{2}.\\d{2}\\.\\d{4}(.*?)</td>").matcher(sectionElement);
                if (nameMatcher.find()) {
                    String nameElement = nameMatcher.group();
                    nameElement = nameElement.substring(nameElement.indexOf(">") + 2, nameElement.indexOf("<") - 1);
                    name = nameElement;
                }

                Matcher oneRowMatcher = Pattern.compile("\">(.*?)</td>").matcher(sectionElement);
                String element = "";
                int index = 0;
                while (oneRowMatcher.find()) {
                    String oneRow = oneRowMatcher.group().replace("\"> ", "").replace(" </td>", "").replace("&" + "nbsp;", "");
                    if(!oneRow.contains("lekcja") && !oneRow.contains("opis") && !oneRow.contains("zastępca") && !oneRow.contains("po lekcji") && !oneRow.contains("miejsce")
                            && !oneRow.contains("poniedziałek") && !oneRow.contains("wtorek") && !oneRow.contains("środa") && !oneRow.contains("czwartek") && !oneRow.contains("piątek")) {
                        if (oneRow.contains("dyżury")) {
                            substitute = element.substring(0, element.length() - 1);
                            element = "";
                        } else {
                            if (index == 0 || index % 3 == 0) {
                                element = element + oneRow;
                            } else if (index == 1 ) {
                                element = element + " -> " + oneRow;
                            } else if (index == 2 && !oneRow.equals("")) {
                                element = element + " -> " + oneRow + "\n";
                            } else if (index == 2 && oneRow.equals("")) {
                                element = element + "\n";
                            }

                            Log.w("UWAGA", "index=" +index +", oneRow=" + oneRow);
                            index++;

                            if(index == 3) {
                                index = 0;
                            }
                        }
                    }
                }
                onDuty = element.substring(0, element.length() - 1);

                teacherArray.add(new Teacher(name, substitute, onDuty));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return teacherArray;
    }
}
