import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SourceReader {

    static List<String> getPathsFromSourceFile(String sourceFileName) {
        BufferedReader br = null;
        List<String> result = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(sourceFileName));
            String s;
            while ((s = br.readLine()) != null) {
                SourceValidator.validateSourceFile(new File(s));
                result.add(s);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
//                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    static long doCount(String path) {
        long count = 0;
        File f = new File(path);
        File[] files = f.listFiles();

        while (!Thread.currentThread().isInterrupted()) {
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        count += doCount(file.getAbsolutePath());
                    } else {
                        count++;
                    }
                }
            }
            return count;
        }
        return count;

    }
}