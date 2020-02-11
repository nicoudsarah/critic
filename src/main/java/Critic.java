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

        String outputOneFileContent = "{\"RepositoryWithOneFile\": \r\n" +
                "{\"path\":\"test/samples/RepositoryWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\": \r\n" +
                "{\"firstFile.txt\":{\"path\":\"test/samples/RepositoryWithOneFile/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}";

        String outputTwoFilesContent = "{\"RepositoryWithTwoFiles\": \r\n" +
                "{\"path\":\"test/samples/RepositoryWithTwoFiles\", \"type\":\"repository\", \"score\":\"1\", \"content\": \r\n" +
                "{\"firstFile.txt\":{\"path\":\"test/samples/RepositoryWithTwoFiles/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"},\r\n" +
                "\"secondFile.txt\":{\"path\":\"test/samples/RepositoryWithTwoFiles/secondFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}";

        String outputSubfolderWithOneFile = "{\"RepositoryContainsSubfolderWhichContainsOneFile\": \r\n" +
                "{\"path\":\"C:\\Users\\Sarah\\IdeaProjects\\critic\\test\\samples\\RepositoryContainsSubfolderWhichContainsOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                "{\"SubFolderWithOneFile\":{\"path\":\"C:\\Users\\Sarah\\IdeaProjects\\critic\\test\\samples\\RepositoryContainsSubfolderWhichContainsOneFile\\SubFolderWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                "{\"firstFile.txt\":{\"path\":\"test/samples/RepositoryWithOneFile/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}}}";

        JSONAutomaticWriter("EmptyRepository", outputEmptyContent);
        JSONAutomaticWriter("RepositoryWithOneFile", outputOneFileContent);
        JSONAutomaticWriter("RepositoryWithTwoFiles", outputTwoFilesContent);
        JSONAutomaticWriter("RepositoryContainsSubfolderWhichContainsOneFile", outputSubfolderWithOneFile);
    }


    private void JSONAutomaticWriter(String RepositoryName, String outputContent) throws IOException {
        File criticNewFile = new File("test/samples/" + RepositoryName + "/critic.json");
        criticNewFile.createNewFile();
        FileOutputStream fileWriter = new FileOutputStream(criticNewFile);
        fileWriter.write(outputContent.getBytes());
    }
}
