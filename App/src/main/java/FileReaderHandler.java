import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderHandler implements AutoCloseable {
    private final BufferedReader reader;

    public FileReaderHandler(File file) throws IOException {

        if (!file.exists()) {
            System.out.println("File Reader not created, file not found");
            throw new IOException("File not found: " + file.getName());
        }

        this.reader = new BufferedReader(new FileReader(file));
    }

    public StringBuilder read() throws IOException {

        String line;
        StringBuilder result = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            result.append(line).append("\n");
        }

        return result;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}

