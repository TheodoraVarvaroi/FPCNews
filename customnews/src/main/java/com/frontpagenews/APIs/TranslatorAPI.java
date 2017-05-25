package com.frontpagenews.APIs;

import com.frontpagenews.APIs.YandexTranslatorAPI.ApiKeys;
import com.frontpagenews.APIs.YandexTranslatorAPI.detect.Detect;
import com.frontpagenews.APIs.YandexTranslatorAPI.language.Language;
import com.frontpagenews.APIs.YandexTranslatorAPI.translate.Translate;

/**
 * Created by Paula Carp on 22.05.2017.
 */
public class TranslatorAPI {

    private TranslatorAPI(){ }

    public static String translate(String text, Language textLanguage, Language resultedLanguage){
        Translate.setKey(ApiKeys.getAPIKey());
        String translatedText = null;
        try {
            translatedText = Translate.execute(text, textLanguage , resultedLanguage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return translatedText;
    }
    public static Language detectLanguage(String text){
        Detect.setKey(ApiKeys.getAPIKey());
        Language language = null;
        try {
            language = Detect.execute(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return language;
    }

}
