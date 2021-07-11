package ml.pkom.generatorpapermcmod;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ml.pkom.easyapi.ResourceControll;

public class Config {
    private static String configData = "";
    private static Map<String, Object> map = null;

    public Config() {
        configData = ResourceControll.fileReadContents("config.json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            map = mapper.readValue(configData, new TypeReference<Map<String, Object>>(){});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Object> getMap() {
        return map;
    }
}
