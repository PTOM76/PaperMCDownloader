package ml.pkom.easyapi;

public class HexConvert {
    public static Hex bin2hex(String data){
        StringBuffer stringBuffer = new StringBuffer();
        for (byte bytes : data.getBytes()) {
            String string = Integer.toHexString(0xff & bytes);
            if (string.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(string);
        }
        return new Hex(stringBuffer.toString());
    }

    public static String bin2hex_s(String data) {
        return bin2hex(data).get();
    }
    
    public static String hex2bin_s(String hex) {
        return hex2bin(hex);
    }

    public static String hex2bin(String hex) {
        return hex2bin(new Hex(hex));
    }

    public static String hex2bin(Hex hex) {
        byte[] bytes = new byte[hex.get().length() / 2];
        for (int index = 0; index < bytes.length; index++) {
            bytes[index] = (byte) Integer.parseInt(hex.get().substring(index * 2, (index + 1) * 2), 16);
        }
        return new String(bytes);
    }
}
