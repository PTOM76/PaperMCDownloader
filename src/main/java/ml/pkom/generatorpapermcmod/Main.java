package ml.pkom.generatorpapermcmod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import ml.pkom.easyapi.CUrl;
import ml.pkom.generatorpapermcmod.classes.MCVersion;
import ml.pkom.generatorpapermcmod.classes.PaperVersion;

public class Main {
    private static FileChooser fileChooser = new FileChooser();

    public static void main(String[] args) {
        new JFXPanel();
        new Language();
        new Config();
        new MainFrame();
        fileChooser.setInitialDirectory(new File("./"));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Jar", "*.jar"));
        fileChooser.getExtensionFilters().add(new ExtensionFilter("All", "*"));
    }

    public static List<String> getMCVersionsList() {
        List<String> list = new ArrayList<String>();
        CUrl cUrl = CUrl.init((String) Config.getMap().get("mc.versions.url"));
        CUrl.setContentType(cUrl, CUrl.CONTENTTYPE_APP_JSON);
        CUrl.setMethod(cUrl, CUrl.METHOD_GET);
        CUrl.exec(cUrl);
        String res = CUrl.res(cUrl);
        CUrl.close(cUrl);
        JSONObject jsonObj = new JSONObject(res);
        JSONArray jsonArray = jsonObj.getJSONArray("versions");
        for (Object object : jsonArray) {
            String version = (String) object;
            list.add(version);
        }
        Collections.reverse(list);
        return list;
    }

    public static List<String> getPaperMCBuildsList(MCVersion mcVersion) {
        List<String> list = new ArrayList<String>();
        CUrl cUrl = CUrl.init(((String) Config.getMap().get("paper.builds.url")).replace("$mcversion$", mcVersion.versionText));
        CUrl.setContentType(cUrl, CUrl.CONTENTTYPE_APP_JSON);
        CUrl.setMethod(cUrl, CUrl.METHOD_GET);
        CUrl.exec(cUrl);
        String res = CUrl.res(cUrl);
        CUrl.close(cUrl);
        JSONObject jsonObj = new JSONObject(res);
        JSONArray jsonArray = jsonObj.getJSONArray("builds");
        for (Object object : jsonArray) {
            String version = ((Integer) object).toString();
            list.add(version);
        }
        Collections.reverse(list);
        return list;
    }

    private static File downloadFilePath;

    public static void downloadPaperMC(PaperVersion version) {
        downloadFilePath = new File("./", "Paper-" + version.mcVersionText + "-" + version.modVersionText + ".jar");
        Platform.runLater(() -> {
            fileChooser.setTitle("Download \"Paper-" + version.mcVersionText + "-" + version.modVersionText + ".jar\"");
            fileChooser.setInitialFileName("Paper-" + version.mcVersionText + "-" + version.modVersionText + ".jar");
            downloadFilePath = fileChooser.showSaveDialog(null);
            String downloadURL = (String) Config.getMap().get("paper.download.url");
            downloadURL = downloadURL.replace("$mcversion$", version.mcVersionText);
            downloadURL = downloadURL.replace("$paperbuild$", version.modVersionText);
            System.out.println(downloadURL);
            try {
                URL url = new URL(downloadURL);
                InputStream in = url.openStream();
                ReadableByteChannel rbc = Channels.newChannel(in);
                FileOutputStream fos = new FileOutputStream(downloadFilePath);
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                fos.close();
                in.close();
                rbc.close();
                String message = Language.get("dialog.download");
                message = message.replace("$name$", "PaperMC v" + version.mcVersionText + "-" + version.modVersionText);
                JOptionPane.showMessageDialog(MainFrame.instance, message);
            } catch(NullPointerException e) {
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
