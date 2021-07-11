package ml.pkom.easyapi;

import java.net.URI;

public class File extends java.io.File {
    
    public File(java.io.File parent, String child) {
        super(parent, child);
    }

    public File(String parent, String child) {
        super(parent, child);
    }

    public File(String pathname) {
        super(pathname);
    }

    private File(URI uri) {
        super(uri);
    }


    
    /**
     * ファイルにデータを書き込みます。
     * 失敗した場合falseを返します。
     * 
     * @param contents データ
     */
    public boolean write(String contents) {
        return FileControll.fileWriteContents(this, contents);
    }

    /**
     * ファイルからデータを読み込みます。
     * 失敗した場合nullを返します。
     * 
     * @return ファイルのデータ or null
     */
    public String read() {
        return FileControll.fileReadContents(this);
    }
}
