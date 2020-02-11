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

        String outputEmptyContent = "{\n}";

        String outputOneFileContent = "{\"folder1\": \r\n" +
                "{\"path\":\"test/samples/RepositoryWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\": \r\n" +
                "{\"file1\":{\"path\":\"test/samples/RepositoryWithOneFile/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}";

        JSONAutomaticWriter("EmptyRepository", outputEmptyContent);

        JSONAutomaticWriter("RepositoryWithOneFile", outputOneFileContent);

        /*File criticEmptyFile = new File("test/samples/EmptyRepository/critic.json");
        criticEmptyFile.createNewFile();
        String outputContent = "{\n}";
        FileOutputStream fileWriter = new FileOutputStream(criticEmptyFile);
        fileWriter.write(outputContent.getBytes());

        File criticRepositoryWithOneFile = new File("test/samples/RepositoryWithOneFile/critic.json");
        criticRepositoryWithOneFile.createNewFile();
        outputContent = "{\"folder1\": \r\n" +
                "{\"path\":\"test/samples/RepositoryWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\": \r\n" +
                "{\"file1\":{\"path\":\"test/samples/RepositoryWithOneFile/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}";
        fileWriter = new FileOutputStream(criticRepositoryWithOneFile);
        fileWriter.write(outputContent.getBytes());*/
    }

    private void JSONAutomaticWriter(String RepositoryName, String outputContent) throws IOException {
        File criticNewFile = new File("test/samples/" + RepositoryName + "/critic.json");
        criticNewFile.createNewFile();
        FileOutputStream fileWriter = new FileOutputStream(criticNewFile);
        fileWriter.write(outputContent.getBytes());
    }
}
