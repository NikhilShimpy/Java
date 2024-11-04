import java.io.FileInputStream;
//import java.io.IOException;

public class FileInputStreamExample {
    public static void main(String[] args) {
        try {
            FileInputStream Fin = new FileInputStream("ReadFile.txt");
                int bytedata;
                while((bytedata = Fin.read()) != -1) {
                    System.out.print((char)bytedata);
                    
                }   
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println(e);
        }
    }

    
} ;