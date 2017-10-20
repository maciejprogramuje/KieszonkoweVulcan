package commaciejprogramuje.facebook.kieszonkowevulcan.DataFragmentGenerator;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Generator {
    public static String dataForTeachersFragment() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Document document = Jsoup.connect("http://zastepstwa.g16-lublin.eu/").get();
            String temp = document.body().toString();

            System.out.println(temp);

            Matcher rowMatcher = Pattern.compile("<tr>(.|\\n)*?</tr>").matcher(temp);
            while (rowMatcher.find()) {
                String row = rowMatcher.group();
                String element = "";

                if (row.contains("Zastępstwa")) {
                    element = row.substring(row.indexOf("<nobr>") + 14, row.indexOf("</nobr>"));
                    stringBuilder.append(element).append("\n");
                } else {
                    Matcher matcher = Pattern.compile("\">(.*?)</td>").matcher(row);
                    int index = 0;
                    while (matcher.find()) {
                        String tempElement = matcher.group().replace("\"> ", "").replace(" </td>", "").replace("&" + "nbsp;", "");

                        Log.w("UWAGA", "szczegóły: [" + tempElement + "]");

                        if (!tempElement.equals("lekcja")
                                && !tempElement.equals("opis")
                                && !tempElement.equals("zastępca")
                                && !tempElement.equals("po lekcji")
                                && !tempElement.equals("miejsce")) {
                            if (index == 0 && !tempElement.equals("")) {
                                    element = tempElement;

                            } else if (index == 1) {
                                if(!tempElement.equals("")) {
                                    element = element + " -> " + tempElement;
                                }
                            } else if (index == 2) {
                                if(tempElement.equals("")) {
                                    element += "\n";
                                } else {
                                    element = element + " -> " + tempElement + "\n";
                                }
                            }
                            index++;
                        }
                    }
                    if (index == 1) {
                        stringBuilder.append("\n");
                    }

                    if (!element.contains("->  ->")) {
                        stringBuilder.append(element);
                    }

                    if (index == 1) {
                        stringBuilder.append("\n");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
