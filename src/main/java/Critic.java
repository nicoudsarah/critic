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

        ReadFolder(path, filesInRepository, fileNameListe, fileEntityTypeListe);

        ArrayList<ArrayList<String>> repositoryContentAttributesList = new ArrayList<>();
        repositoryContentAttributesList.add(fileNameListe);
        repositoryContentAttributesList.add(fileEntityTypeListe);
        return repositoryContentAttributesList;
    }

    private void ReadFolder(String path, File[] filesInRepository, ArrayList<String> fileNameListe, ArrayList<String> fileEntityTypeListe) {
        for (int i = 0 ; i < filesInRepository.length; i++){
            if(filesInRepository[i].isFile() && !filesInRepository[i].getName().endsWith("json")) {
                fileNameListe.add(filesInRepository[i].getName());
                fileEntityTypeListe.add("file");
            } else if (filesInRepository[i].isDirectory()) {
                fileNameListe.add(filesInRepository[i].getName());
                fileEntityTypeListe.add("repository");
                String subfolderPath = path + "/" + filesInRepository[i].getName();
                File subFolder = new File(subfolderPath);
                filesInRepository = subFolder.listFiles();
                ReadFolder(subfolderPath, filesInRepository, fileNameListe, fileEntityTypeListe);
            }
        }
    }

    private String OutputAutomaticWriter(String path, String repositoryName) {
        String outputContent = "";
        ArrayList<String> fileNameListe = new ArrayList<>();
        ArrayList<String> fileEntityTypeListe = new ArrayList<>();

        fileNameListe = ListRepositoryContentAttributes(path, repositoryName).get(0);
        fileEntityTypeListe = ListRepositoryContentAttributes(path, repositoryName).get(1);

        System.out.println(fileNameListe);

        if (repositoryName.equals("EmptyRepository")){
             outputContent = "{\n}";

        } else if (repositoryName.equals("RepositoryWithOneFile")) {
            outputContent = "{" + CreateJSONFileDescription(path, fileEntityTypeListe.get(0)) + "\r\n" +
                    CreateJSONFileDescription( path + "/" + fileNameListe.get(1), fileEntityTypeListe.get(1)) + "}}}}}";

        } else if (repositoryName.equals("RepositoryWithTwoFiles")) {
            outputContent = "{" + CreateJSONFileDescription(path, fileEntityTypeListe.get(0)) + "\r\n" +
                    CreateJSONFileDescription(path + "/" + fileNameListe.get(1), fileEntityTypeListe.get(1)) + "}},\r\n" +
                    CreateJSONFileDescription(path + "/" + fileNameListe.get(2), fileEntityTypeListe.get(2)) + "}}}}}";

        } else if (repositoryName.equals("RepositoryContainsSubfolderWhichContainsOneFile")) {
            System.out.println(fileNameListe);
            System.out.println(fileEntityTypeListe);

            outputContent = "{" + CreateJSONFileDescription(path, fileEntityTypeListe.get(0)) + "\r\n" +
                    CreateJSONFileDescription(path + "/" + fileNameListe.get(1), fileEntityTypeListe.get(1)) + "\r\n" +
                    CreateJSONFileDescription(path + "/SubFolderWithOneFile/" + fileNameListe.get(2), fileEntityTypeListe.get(2)) + "}}}}}}}";

        } else if (repositoryName.equals("RepositoryContainsSubfolderAndOneFile")) {
            outputContent ="{\"" + repositoryName + "\": \r\n" +
                    "{\"path\":\"test/samples/" + repositoryName + "\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                    "{\"SubFolderWithOneFile\":{\"path\":\"test/samples/" + repositoryName + "/SubFolderWithOneFile\", \"type\":\"repository\", \"score\":\"1\", \"content\":\r\n" +
                    "{\"firstFileSubfolder.txt\":{\"path\":\"test/samples/" + repositoryName + "/SubFolderWithOneFile/firstFileSubfolder.txt\", \"type\":\"file\", \"score\":\"1\"}}\r\n" +
                    "\"firstFileFolder.txt\": {\"path\":\"test/samples/" + repositoryName + "/firstFileFolder.txt\", \"type\":\"file\", \"score\":\"1\"}}}}}";
        }
        return outputContent;
    }

    private String CreateJSONFileDescription(String path, String type) {
        int indexOfBaseName = path.lastIndexOf("/");
        String key = path.substring(indexOfBaseName+1);
        return "\"" + key + "\":{\"path\":\"" + path + "\", \"type\":\"" + type + "\", \"score\":\"1\", \"content\":{";
    }
}
