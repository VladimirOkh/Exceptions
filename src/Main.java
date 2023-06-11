import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;


class ConvertException extends Exception {
    ConvertException(String message) {
        super(message);
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            copyFileUsingStream("src/utf8.txt", "UTF-8", "src/win1251.txt", "WINDOWS-1251");
            System.out.println("Перекодировка прошла успешно");
        } catch (ConvertException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void copyFileUsingStream(String source, String sourceEnc, String dest, String destEnc) throws ConvertException {
        Charset sEnc = null;
        try (Reader fis = new InputStreamReader(new FileInputStream(source), Charset.forName(sourceEnc));
             Writer fos = new OutputStreamWriter(new FileOutputStream(dest), Charset.forName(destEnc));) {
            char[] buffer = new char[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            throw new ConvertException("Проблема с файлами: " + e.getMessage());
        } catch (UnsupportedCharsetException e) {
            throw new ConvertException("Проблема с кодировкой: " + e.getMessage());
        } catch (IOException e) {
            throw new ConvertException("Проблема с копированием");
        }
    }
}
