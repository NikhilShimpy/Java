import java.io.FileOutputStream;
public class FileOutputStreamAppend {
    public static void main(String[] args) {
        //same code but add second parameter"true" in outputstream
        try {
            FileOutputStream FopA = new FileOutputStream("ReadFile.txt",true);
            String data = "\nThis is OutputStream";
            FopA.write(data.getBytes());
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
        
    }
    
}
