package ml.pkom.easyapi;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceControll {
    public static String fileReadContents(String fileName) {
        InputStream in = ResourceControll.class.getResourceAsStream("/" + fileName);
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line, data = "";
            while ((line = bufferedReader.readLine()) != null) {
                data += line + "\n";
            }
            bufferedReader.close();
            return data;  
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
