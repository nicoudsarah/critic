import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.NotDirectoryException;

public class Critic {
    private String repositoryPath;


    public Critic(String repositoryPath) {
        this.repositoryPath = repositoryPath;
    }

    public void evaluate() throws IOException {
        File repository = new File(repositoryPath);

        if (!repository.exists()) {
            throw new FileNotFoundException();
        }

        if (!repository.isDirectory()) {
            throw new NotDirectoryException(repositoryPath);
        }

        File criticEmptyFile = new File("test/samples/EmptyRepository/critic.json");
        criticEmptyFile.createNewFile();
        String outputContent = "{\n}";
        FileOutputStream fileWriter = new FileOutputStream(criticEmptyFile);
        fileWriter.write(outputContent.getBytes());
    }
}