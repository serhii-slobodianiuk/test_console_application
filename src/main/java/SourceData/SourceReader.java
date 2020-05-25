package SourceData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SourceReader {

    public static List<String> getPathsFromSourceFile(String sourceFileName) {
        BufferedReader br = null;
        List<String> result = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(sourceFileName));
            String s;
            while ((s = br.readLine()) != null) {
                if (SourceValidator.validateSourceFile(new File(s))) {
                    result.add(s);
                }
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ignored) {

                }
            }
        }
        return result;
    }
}