import java.io.File;
import java.io.IOException;

public class FileHandler {
    private final FileReaderHandler reader;
    private final FileWriterHandler writer;

    public FileHandler(File file) throws IOException {

        if (!file.exists()) {
            System.out.println("File Handler not created, file does not exist");
            throw new IOException("File not found: " + file.getName());
        }

        this.reader = new FileReaderHandler(file);
        this.writer = new FileWriterHandler(file);
    }

    public StringBuilder read() throws IOException {
        return reader.read();
    }

    public void write(String data) throws IOException {
        writer.write(data);
    }

    public FileWriterHandler getWriter() {
        return this.writer;
    }

    public FileReaderHandler getReader() {
        return this.reader;
    }
}
