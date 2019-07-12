package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


 public class FileUtil {

    private Scanner in;

    public String retrieveFileContents(final String fileName) {
        File song = new File(getClass().getClassLoader().getResource(fileName).getFile());
        try {
            in = new Scanner(song);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder data = new StringBuilder();
        while (in.hasNext()) {
            data.append(in.nextLine()).append(" ");
        }
        return data.toString();
    }
}
