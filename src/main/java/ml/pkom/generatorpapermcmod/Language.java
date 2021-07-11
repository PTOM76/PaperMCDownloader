package ml.pkom.generatorpapermcmod;

import java.util.Locale;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ml.pkom.easyapi.ResourceControll;

public class Language {
    private static Map<String, Object> map = null;
    public static String lang = "ja_jp";

    public Language() {
        if (Locale.getDefault().getLanguage().equals(new Locale("ja").getLanguage())) lang = "ja_jp";
        if (Locale.getDefault().getLanguage().equals(new Locale("us").getLanguage())) lang = "en_us";
        
        String configData = ResourceControll.fileReadContents("lang/" + lang + ".json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(configData, new TypeReference<Map<String, Object>>(){});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeLanguage(String langName) {
        lang = langName;
        String configData = ResourceControll.fileReadContents("lang/" + lang + ".json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(configData, new TypeReference<Map<String, Object>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        MainFrame.instance.removeAll();
        MainFrame.instance.setVisible(false);
        MainFrame.instance = null;
        new MainFrame();
    }

    public static Map<String, Object> getMap() {
        return map;
    }

    public static String get(String key) {
        return (String) getMap().get(key);
    }
}
