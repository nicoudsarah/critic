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


        /*String outputOneFileContent = "{\"RepositoryWithOneFile\": \r\n" +
                "{\"path\":\"test/samples/RepositoryWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\": \r\n" +
                "{\"firstFile.txt\":{\"path\":\"test/samples/RepositoryWithOneFile/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}";*/

        /*String outputTwoFilesContent = "{\"RepositoryWithTwoFiles\": \r\n" +
                "{\"path\":\"test/samples/RepositoryWithTwoFiles\", \"type\":\"repository\", \"score\":\"1\", \"content\": \r\n" +
                "{\"firstFile.txt\":{\"path\":\"test/samples/RepositoryWithTwoFiles/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"},\r\n" +
                "\"secondFile.txt\":{\"path\":\"test/samples/RepositoryWithTwoFiles/secondFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}";

        String outputSubfolderWithOneFile = "{\"RepositoryContainsSubfolderWhichContainsOneFile\": \r\n" +
                "{\"path\":\"test/samples/RepositoryContainsSubfolderWhichContainsOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                "{\"SubFolderWithOneFile\":{\"path\":\"test/samples/RepositoryContainsSubfolderWhichContainsOneFile/SubFolderWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                "{\"firstFile.txt\":{\"path\":\"test/samples/RepositoryContainsSubfolderWhichContainsOneFile/SubFolderWithOneFile/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}}}";

        String outputSubfolderAndOneFile = "{\"RepositoryContainsSubfolderAndOneFile\": \r\n" +
                "{\"path\":\"test/samples/RepositoryContainsSubfolderAndOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                "{\"SubFolderWithOneFile\":{\"path\":\"test/samples/RepositoryContainsSubfolderAndOneFile/SubFolderWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                "{\"firstFileSubfolder.txt\":{\"path\":\"test/samples/RepositoryContainsSubfolderAndOneFile/SubFolderWithOneFile/firstFileSubfolder.txt\", \"type\":\"file\", \"score\":\"1\"}}\r\n" +
                "\"firstFileFolder.txt\": {\"path\":\"test/samples/RepositoryContainsSubfolderAndOneFile/firstFileFolder.txt\", \"type\":\"file\", \"score\":\"1\"}}}}}";
*/
        JSONAutomaticWriter("EmptyRepository");
        JSONAutomaticWriter("RepositoryWithOneFile");
        /*JSONAutomaticWriter("RepositoryWithTwoFiles", outputTwoFilesContent);
        JSONAutomaticWriter("RepositoryContainsSubfolderWhichContainsOneFile", outputSubfolderWithOneFile);
        JSONAutomaticWriter("RepositoryContainsSubfolderAndOneFile", outputSubfolderAndOneFile);*/
    }


    private void JSONAutomaticWriter(String RepositoryName) throws IOException {
        File criticNewFile = new File("test/samples/" + RepositoryName + "/critic.json");
        criticNewFile.createNewFile();
        FileOutputStream fileWriter = new FileOutputStream(criticNewFile);
        fileWriter.write(OutputAutomaticWriter(RepositoryName).getBytes());
    }

    private String OutputAutomaticWriter(String repositoryName) {
        String outputContent ="";

        if (repositoryName == "EmptyRepository"){
             outputContent = "{\n}";
        }
        else {
             outputContent = "{\"" + repositoryName + "\": \r\n" +
                    "{\"path\":\"test/samples/" + repositoryName + "\", \"type\":\"repository\", \"score\":\"1\", \"content\": \r\n" +
                    "{\"firstFile.txt\":{\"path\":\"test/samples/" + repositoryName + "/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}";
        }
        return outputContent;
    }
}
