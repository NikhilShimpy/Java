import java.io.FileOutputStream;

public class FileOutputStreamExample {
    public static void main(String[] args) {
        try {
            FileOutputStream Fop = new FileOutputStream("ReadFile.txt");
            String data = "\nThis is OutputStream";
            Fop.write(data.getBytes());
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
    }
}
