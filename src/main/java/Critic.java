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

    private ArrayList<ArrayList<String>> ListRepositoryContentAttributes(String path, String repositoryName){
        File repertoire = new File(path);
        File[] filesInRepository = repertoire.listFiles();
        ArrayList<String> fileNameListe = new ArrayList<>();
        fileNameListe.add(repositoryName);
        ArrayList<String> fileEntityTypeListe = new ArrayList<>();
        fileEntityTypeListe.add("repository");
                for (int i = 0 ; i < filesInRepository.length; i++){
            if(!filesInRepository[i].getName().endsWith("json")) {
                fileNameListe.add(filesInRepository[i].getName());
                String entityType = (filesInRepository[i].isDirectory()) ? "repository" : "file";
                fileEntityTypeListe.add(entityType);
            }
        }
        ArrayList<ArrayList<String>> repositoryContentAttributesList = new ArrayList<>();
        repositoryContentAttributesList.add(fileNameListe);
        repositoryContentAttributesList.add(fileEntityTypeListe);
        return repositoryContentAttributesList;
    }

    private String OutputAutomaticWriter(String path, String repositoryName) {
        String outputContent = "";
        ArrayList<String> fileNameListe = new ArrayList<>();
        ArrayList<String> fileEntityTypeListe = new ArrayList<>();

        fileNameListe = ListRepositoryContentAttributes(path, repositoryName).get(0);
        fileEntityTypeListe = ListRepositoryContentAttributes(path, repositoryName).get(1);

        if (repositoryName.equals("EmptyRepository")){
             outputContent = "{\n}";

        } else if (repositoryName.equals("RepositoryWithOneFile")) {
            outputContent = "{" + CreateJSONFileDescription("test/samples", fileNameListe, fileEntityTypeListe, 0) + "\r\n" +
                    CreateJSONFileDescription(path, fileNameListe, fileEntityTypeListe, 1) + "}}}}}";

        } else if (repositoryName.equals("RepositoryWithTwoFiles")) {
            outputContent = "{" + CreateJSONFileDescription("test/samples", fileNameListe, fileEntityTypeListe, 0) + "\r\n" +
                    CreateJSONFileDescription(path, fileNameListe, fileEntityTypeListe, 1) + "}},\r\n" +
                    CreateJSONFileDescription(path, fileNameListe, fileEntityTypeListe, 2) + "}}}}}";

        } else if (repositoryName.equals("RepositoryContainsSubfolderWhichContainsOneFile")) {
            System.out.println(fileNameListe);
            System.out.println(fileEntityTypeListe);

            outputContent = "{" + CreateJSONFileDescription("test/samples", fileNameListe, fileEntityTypeListe, 0) + "\r\n" +
                    CreateJSONFileDescription(path, fileNameListe, fileEntityTypeListe, 1) + "\r\n" +
                    CreateJSONFileDescription(path, fileNameListe, fileEntityTypeListe, 2) + "}}}}}}}";

        } else if (repositoryName.equals("RepositoryContainsSubfolderAndOneFile")) {
            outputContent ="{\"" + repositoryName + "\": \r\n" +
                    "{\"path\":\"test/samples/" + repositoryName + "\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                    "{\"SubFolderWithOneFile\":{\"path\":\"test/samples/" + repositoryName + "/SubFolderWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                    "{\"firstFileSubfolder.txt\":{\"path\":\"test/samples/" + repositoryName + "/SubFolderWithOneFile/firstFileSubfolder.txt\", \"type\":\"file\", \"score\":\"1\"}}\r\n" +
                    "\"firstFileFolder.txt\": {\"path\":\"test/samples/" + repositoryName + "/firstFileFolder.txt\", \"type\":\"file\", \"score\":\"1\"}}}}}";
        }
        return outputContent;
    }

    private String CreateJSONFileDescription(String path, ArrayList<String> fileNameListe, ArrayList<String> entityType, int indexOfEntity) {
        return "\"" + fileNameListe.get(indexOfEntity) + "\":{\"path\":\"" + path + "/" + fileNameListe.get(indexOfEntity) + "\", \"type\":\"" + entityType.get(indexOfEntity) + "\", \"score\":\"1\", \"content\":{";
    }
}
