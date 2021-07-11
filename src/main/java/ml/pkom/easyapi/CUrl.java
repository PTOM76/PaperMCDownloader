package ml.pkom.easyapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class CUrl {
    public static final int METHOD_GET = 1;
    public static final int METHOD_POST = 2;
    public static final String CONTENTTYPE_TEXT_PLAIN = "text/plain";
    public static final String CONTENTTYPE_TEXT_XML = "text/xml";
    public static final String CONTENTTYPE_TEXT_HTML = "text/html";
    public static final String CONTENTTYPE_TEXT_CSV = "text/csv";
    public static final String CONTENTTYPE_TEXT_CSS = "text/css";
    public static final String CONTENTTYPE_TEXT_JAVASCRIPT = "text/javascript";
    public static final String CONTENTTYPE_APP_OCTET_STREAM = "application/octet-stream";
    public static final String CONTENTTYPE_APP_JSON = "application/json";
    public static final String CONTENTTYPE_APP_XML = "application/xml";
    public static final String CONTENTTYPE_APP_PDF = "application/pdf";
    public static final String CONTENTTYPE_APP_XLS = "application/vnd.ms-excel";
    public static final String CONTENTTYPE_APP_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String CONTENTTYPE_APP_PPT = "application/vnd.ms-powerpoint";
    public static final String CONTENTTYPE_APP_PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String CONTENTTYPE_APP_DOC = "application/msword";
    public static final String CONTENTTYPE_APP_DOCX = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String CONTENTTYPE_APP_ZIP = "application/zip";
    public static final String CONTENTTYPE_APP_X_LZH = "application/x-lzh";
    public static final String CONTENTTYPE_APP_X_TAR = "application/x-tar";
    public static final String CONTENTTYPE_AUDIO_MPEG = "audio/mpeg";
    public static final String CONTENTTYPE_AUDIO_MP3 = CONTENTTYPE_AUDIO_MPEG;
    public static final String CONTENTTYPE_AUDIO_WAV = "audio/wav";
    public static final String CONTENTTYPE_VIDEO_MPEG = "video/mpeg";
    public static final String CONTENTTYPE_VIDEO_MP4 = "video/mp4";
    public static final String CONTENTTYPE_IMAGE_JPEG = "image/jpeg";
    public static final String CONTENTTYPE_IMAGE_PNG = "image/png";
    public static final String CONTENTTYPE_IMAGE_GIF = "image/gif";
    public static final String CONTENTTYPE_IMAGE_BMP = "image/bmp";
    public static final String CONTENTTYPE_IMAGE_SVG = "image/svg+xml";
    public static final String ENCODE_UTF_8 = "UTF-8";

    public static CUrl init(String url) {
        return new CUrl(url);
    }

    public static boolean exec(CUrl curl) {
        if (curl.getURL() == null)
            return false;
        try {
            curl.connection = (HttpURLConnection) curl.getURL().openConnection();
            curl.connection.setRequestProperty("Content-Type", curl.getContentType() + ";charset=" + curl.getEncode());
            if (curl.getUserAgent() != null) {
                curl.connection.setRequestProperty("User-Agent", curl.getUserAgent());
            }
            if (curl.getAcceptLanguage() != null) {
                curl.connection.setRequestProperty("Accept-Language", curl.getAcceptLanguage());
            }
            curl.connection.setRequestMethod(curl.getMethodName());
            curl.connection.setDoInput(true);
            curl.connection.connect();
            curl.code = curl.connection.getResponseCode();
            curl.res = convertToString(curl.connection.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static int resCode(CUrl curl) {
        return curl.code;
    }

    public static String res(CUrl curl) {
        return curl.res;
    }

    public static boolean close(CUrl curl) {
        curl.connection.disconnect();
        curl.connection = null;
        return true;
    }

    public static void setMethod(CUrl curl, int method) {
        curl.setMethod(method);
    }

    public static void setContentType(CUrl curl, String contentType) {
        curl.setContentType(contentType);
    }

    public static void setEncode(CUrl curl, String encode) {
        curl.setEncode(encode);
    }

    public static void setUserAgent(CUrl curl, String userAgent) {
        curl.setUserAgent(userAgent);
    }

    private HttpURLConnection connection;
    private URL url = null;
    private int method = METHOD_GET;
    private String contentType = CONTENTTYPE_TEXT_HTML;
    private String encode = ENCODE_UTF_8;
    private String user_agent = null;
    private String accept_language = Locale.getDefault().toString();

    public Integer code = null;
    public String res = null;

    public CUrl(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public CUrl(URL url) {
        this.url = url;
    }

    public URL getURL() {
        return this.url;
    }

    public void setURL(URL url) {
        this.url = url;
    }

    public String getContentType() {
        return this.contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getEncode() {
        return this.encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getUserAgent() {
        return this.user_agent;
    }

    public void setUserAgent(String userAgent) {
        this.user_agent = userAgent;
    }

    public String getAcceptLanguage() {
        return this.accept_language;
    }

    public int getMethod() {
        return this.method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getMethodName() {
        if (this.method == METHOD_GET)
            return "GET";
        if (this.method == METHOD_POST)
            return "POST";
        return "GET";
    }

    public String toString() {
        return this.url.toString();
    }

    public static String convertToString(InputStream stream) throws IOException {
        StringBuffer sb = new StringBuffer();
        String line = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        try {
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
