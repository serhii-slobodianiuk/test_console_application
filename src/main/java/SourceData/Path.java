package SourceData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Path {
    public static List<String> readFrom(Arguments arguments, String sourceFileName) {
        List<String> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                arguments.validateFile(s);
                result.add(s);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}