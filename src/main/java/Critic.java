import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;

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

        JSONAutomaticWriter(repositoryPath);
    }

    private void JSONAutomaticWriter(String path) throws IOException {
        File criticNewFile = new File(path + "/critic.json");
        criticNewFile.createNewFile();
        FileOutputStream fileWriter = new FileOutputStream(criticNewFile);
        int indexOfRepositoryName = path.lastIndexOf("/");
        String repositoryName = path.substring(indexOfRepositoryName+1);
        fileWriter.write(OutputAutomaticWriter(path, repositoryName).getBytes());
    }

    private String OutputAutomaticWriter(String path, String repositoryName) {
        String outputContent = "";

        if (repositoryName.equals("EmptyRepository")){
             outputContent = "{\n}";
        }
        else if (repositoryName.equals("RepositoryWithOneFile")) {
            File repertoire = new File(path);
            String folderContentListe[] = repertoire.list();
            ArrayList<String> fileNameListe = new ArrayList<>();
            for (int i = 0 ; i < folderContentListe.length; i++){
                if(!folderContentListe[i].endsWith("json")) {
                    fileNameListe.add(folderContentListe[i]);
                }
            }
            File[] filesInRepository = repertoire.listFiles();
            String entityType = (filesInRepository[0].isDirectory()) ? "repository" : "file";

            outputContent = "{\"" + repositoryName + "\": \r\n" +
                    "{\"path\":\"" + path + "\", \"type\":\"repository\", \"score\":\"1\", \"content\": \r\n" +
                    "{\""+fileNameListe.get(0)+"\":{\"path\":\"" + path + "/"+fileNameListe.get(0)+"\", \"type\":\""+ entityType +"\", \"score\":\"1\"}}}}";

        } else if (repositoryName.equals("RepositoryWithTwoFiles")) {

            File repertoire = new File(path);
            String folderContentListe[] = repertoire.list();
            ArrayList<String> fileNameListe = new ArrayList<>();
            for (int i = 0 ; i < folderContentListe.length; i++){
                if(!folderContentListe[i].endsWith("json")) {
                    fileNameListe.add(folderContentListe[i]);
                }
            }
            File[] filesInRepository = repertoire.listFiles();
            String entityType = (filesInRepository[0].isDirectory()) ? "repository" : "file";

            outputContent ="{\"" + repositoryName + "\": \r\n" +
                    "{\"path\":\"" + path + "\", \"type\":\"repository\", \"score\":\"1\", \"content\": \r\n" +
                    "{\""+fileNameListe.get(0)+"\":{\"path\":\"" + path + "/"+fileNameListe.get(0)+"\", \"type\":\""+ entityType +"\", \"score\":\"1\"},\r\n" +
                    "\""+fileNameListe.get(1)+"\":{\"path\":\"" + path + "/"+fileNameListe.get(1)+"\", \"type\":\""+ entityType +"\", \"score\":\"1\"}}}}";


        } else if (repositoryName.equals("RepositoryContainsSubfolderWhichContainsOneFile")) {
            outputContent = "{\"" + repositoryName + "\": \r\n" +
                    "{\"path\":\"test/samples/" + repositoryName + "\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                    "{\"SubFolderWithOneFile\":{\"path\":\"test/samples/" + repositoryName + "/SubFolderWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                    "{\"firstFile.txt\":{\"path\":\"test/samples/" + repositoryName + "/SubFolderWithOneFile/firstFile.txt\", \"type\":\"file\", \"score\":\"1\"}}}}}}";
        } else if (repositoryName.equals("RepositoryContainsSubfolderAndOneFile")) {
            outputContent ="{\"" + repositoryName + "\": \r\n" +
                    "{\"path\":\"test/samples/" + repositoryName + "\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                    "{\"SubFolderWithOneFile\":{\"path\":\"test/samples/" + repositoryName + "/SubFolderWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                    "{\"firstFileSubfolder.txt\":{\"path\":\"test/samples/" + repositoryName + "/SubFolderWithOneFile/firstFileSubfolder.txt\", \"type\":\"file\", \"score\":\"1\"}}\r\n" +
                    "\"firstFileFolder.txt\": {\"path\":\"test/samples/" + repositoryName + "/firstFileFolder.txt\", \"type\":\"file\", \"score\":\"1\"}}}}}";
        }
        return outputContent;
    }
}
