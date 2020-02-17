import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Critic {
    private String repositoryPath;
    public int nbOfLevel = 0 ;
    public String tabs = "\t\t";

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
        fileWriter.write(GenerateJSON(path).getBytes());
    }

    private ArrayList<File> shallFileBeAnalyzed(File[] filesInRepository) {
        ArrayList<File> filesToAnalyze = new ArrayList<>();
        for (int i = 0 ; i < filesInRepository.length; i++) {
            String name = filesInRepository[i].getName();
            // XXX rustine
            if (filesInRepository[i].isFile() && !name.endsWith("json") && !name.startsWith(".")) {
                filesToAnalyze.add(filesInRepository[i]);
            }
            else if(filesInRepository[i].isDirectory()){
                filesToAnalyze.add(filesInRepository[i]);
            }
        }
        return filesToAnalyze;
    }

    private String GenerateJSON(String path) {
        String content = GenerateJSONContent(path);
        return "{\n" +
                "\t\"path\" : \""+ path +"\",\n" +
                "\t\"type\" : \"directory\",\n" +
                "\t\"score\" : \"1\",\n" +
                "\t\"content\" : [\n" +
                "\t\t{\n" +
                content +
                "\t\t}\n" +
                "\t]\n" +
                "}\n";
    }

    private String GenerateJSONContent(String path) {
        StringBuilder content = new StringBuilder();
        File repository = new File(path);
        File[] filesInRepository = repository.listFiles();

        ArrayList<File> filesToAnalyze = shallFileBeAnalyzed(filesInRepository);

        Path rootPath = Paths.get(repositoryPath);
        int nbElementInRootPath = rootPath.getNameCount();

        for (int i = 0 ; i < filesToAnalyze.size(); i++) {
            String fileName = filesToAnalyze.get(i).getName();

            if (filesToAnalyze.get(i).isDirectory()){
                File directory = filesToAnalyze.get(i);

                Path folderpath = Paths.get(directory.getPath());
                nbOfLevel = folderpath.getNameCount() - nbElementInRootPath;
                content.append(GenerateDirectoryDescription(fileName, directory));
                nbOfLevel = nbOfLevel-1;
            }
            else {
                content.append(GenerateFileDescription(fileName));
                if(i<filesToAnalyze.size()-1) {
                    content.append(tabs.repeat(nbOfLevel)).append("\t\t},\n").append(tabs.repeat(nbOfLevel)).append("\t\t{\n");
                }
            }
        }
        return content.toString();
    }

    private String GenerateFileDescription(String fileName) {
        return tabs.repeat(nbOfLevel) + "\t\t\t\"path\" : \""+ fileName +"\",\n" +
               tabs.repeat(nbOfLevel) + "\t\t\t\"type\" : \"file\",\n" +
               tabs.repeat(nbOfLevel) + "\t\t\t\"score\" : \"1\",\n" +
               tabs.repeat(nbOfLevel) + "\t\t\t\"content\" : [\n" +
               tabs.repeat(nbOfLevel) +  "\t\t\t\t{\n" +
               tabs.repeat(nbOfLevel) + "\t\t\t\t}\n" +
               tabs.repeat(nbOfLevel) + "\t\t\t]\n";
    }

    private String GenerateDirectoryDescription(String fileName, File directory) {

        String description = GenerateJSONContent(directory.getPath());
        return tabs.repeat(nbOfLevel) +"\t\"path\" : \""+ fileName +"\",\n" +
                tabs.repeat(nbOfLevel) +"\t\"type\" : \"directory\",\n" +
                tabs.repeat(nbOfLevel) + "\t\"score\" : \"1\",\n" +
                tabs.repeat(nbOfLevel) +"\t\"content\" : [\n" +
                tabs.repeat(nbOfLevel) +"\t\t{\n" +
                 description +
                tabs.repeat(nbOfLevel) + "\t\t}\n" +
                tabs.repeat(nbOfLevel) + "\t]\n";
    }
}
