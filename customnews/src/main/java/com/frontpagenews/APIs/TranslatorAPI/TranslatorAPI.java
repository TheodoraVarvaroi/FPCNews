package com.frontpagenews.APIs.TranslatorAPI;
/**
 * Created by Paula Carp on 30.05.2017.
 */
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslatorAPI {

    private TranslatorAPI(){ }

    private static String callUrlAndParseResult(String langFrom, String langTo, String word) throws Exception
    {
        String url = "https://translate.googleapis.com/translate_a/single?"+
                "client=gtx&"+
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return parseResult(response.toString());
    }

    private static String parseResult(String inputJson) throws Exception
    {
        String translated = "";
        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        for(int i=0;i<jsonArray2.length();i++)
        {
            JSONArray jsonArray3 = (JSONArray) jsonArray2.get(i);
            translated+=jsonArray3.get(0).toString();
        }

        return translated;
    }
    public static String translate(String text, Language textLanguage, Language resultedLanguage){
        String translatedText="";
        String textPiece;
        int lastIndex=0;
        String[] paragraphs = text.split("\n");
        String toTranslate="";
        try {
        /*if the length of the text is bigger than 3000 characters, we split the text into groups of characters,
            otherwise http request will be too large and get an exception */
            for (int j = 0; j < paragraphs.length; j++) {
                toTranslate += paragraphs[j];
                if(toTranslate.length() > 2000) {
                    if (toTranslate.length() > 3000) {
                        for (int i = 0; i < toTranslate.length() / 3000; i++) {
                            lastIndex = 3000 * i + 2999;
                            textPiece = toTranslate.substring(3000 * i, lastIndex);
                            translatedText += callUrlAndParseResult(textLanguage.toString(), resultedLanguage.toString(), textPiece);
                        }
                        if (lastIndex != toTranslate.length() - 1)
                            translatedText += callUrlAndParseResult(textLanguage.toString(), resultedLanguage.toString(), toTranslate.substring(lastIndex, toTranslate.length() - 1));
                    } else
                        translatedText += callUrlAndParseResult(textLanguage.toString(), resultedLanguage.toString(), toTranslate);
                    translatedText += "\n";
                    toTranslate = "";
                }
                else if(j==paragraphs.length-1)
                    translatedText += callUrlAndParseResult(textLanguage.toString(), resultedLanguage.toString(), toTranslate);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return translatedText;
    }
}
