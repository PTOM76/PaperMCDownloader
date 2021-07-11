package ml.pkom.generatorpapermcmod.classes;

public class PaperVersion {
    public String modVersionText = "";
    public String mcVersionText = "";

    public PaperVersion(String mcVer, String paperVer) {
        mcVersionText = mcVer;
        modVersionText= paperVer;
    }

    public String toString() {
        return modVersionText;
    }
}
