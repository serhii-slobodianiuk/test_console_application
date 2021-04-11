package source;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    private ArgumentsHandler arguments;

    private FileUtils(ArgumentsHandler arguments) {

        this.arguments = arguments;
    }

    public static FileUtils create(ArgumentsHandler arguments) {
        return new FileUtils(arguments);
    }

    public List<Path> read(String sourceFileName) {
        List<Path> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(sourceFileName))) {
            String s;
            while ((s = br.readLine()) != null) {
                arguments.validateFile(s);
                result.add(Paths.get(s));
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}