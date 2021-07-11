package ml.pkom.easyapi;

public class Hex {
    private String hex;
    public Hex(String string) {
        set(string);
    }

    public String get() {
        return hex;
    }

    public void set(String string) {
        if (!string.matches("[0-9a-fA-F]+")) {
            throw new RuntimeException("代入された変数は16進数ではありません。文字列:" + string);
        }
        if (string.length() % 2 != 0) {
            throw new RuntimeException("代入された変数は16進数ではありません。文字列:" + string);
        }
        hex = string;
    }
}
