import java.io.FileReader;

public class CharacterReaderExample {
    public static void main(String[] args) {
        try {
            FileReader FR = new FileReader("FileWriter.txt");
            int data;
            while ((data = FR.read()) != -1) {
                System.err.print((char)data);
                
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
    }
    
}
