import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterHandler implements AutoCloseable {
    private final BufferedWriter writer;

    public FileWriterHandler(File file) throws IOException {

        if (!file.exists()) {
            System.out.println("File Writer not created, file not found");
            throw new IOException("File not found: " + file.getName());
        }

        this.writer = new BufferedWriter(new FileWriter(file));
    }

    public void write(String data) throws IOException {
        writer.write(data);
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
