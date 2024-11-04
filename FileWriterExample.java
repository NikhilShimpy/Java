import java.io.FileWriter;
public class FileWriterExample {
    public static void main(String[] args) {
        try {
            FileWriter fw = new FileWriter("FileWriter.txt",true);
            String data = "\nFile Writer content";
            fw.write(data);
           // fw.write("hey");
            fw.flush();
            
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
    }
    
}
