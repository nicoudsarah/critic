import java.io.*;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Critic {
    private String repositoryPath;
    // TODO : remove this attribute to pass it as function parameter instead (to avoid side effect)
    public int indentationDepth = 0 ;
    final String tabs = "\t";

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

    private String GenerateJSON(String path) throws IOException {
        File rootFile = new File(path);
        FolderDescription content2 = GenerateDirectoryDescription(rootFile);

        return "{\n" +
                content2.getJSONContent() +
                "}\n";
    }

    private FolderDescription GenerateJSONContent(String path) throws IOException {
        FolderDescription description = new FolderDescription();
        StringBuilder content = new StringBuilder();
        File repository = new File(path);
        File[] filesInRepository = repository.listFiles();

        ArrayList<File> filesToAnalyze = shallFileBeAnalyzed(filesInRepository);

        Path rootPath = Paths.get(repositoryPath);
        int nbElementInRootPath = rootPath.getNameCount();
        int score = 0;
        int descriptionScore = 0;

        for (int i = 0 ; i < filesToAnalyze.size(); i++) {
            String fileName = filesToAnalyze.get(i).getName();

            if (filesToAnalyze.get(i).isDirectory()){
                File directory = filesToAnalyze.get(i);

                Path folderpath = Paths.get(directory.getPath());
                indentationDepth = folderpath.getNameCount() - nbElementInRootPath;
                FolderDescription folderDescription = GenerateDirectoryDescription(directory);
                content.append(folderDescription.getJSONContent());
                descriptionScore += folderDescription.getScore();
                //XXX Verifier si c'est une rustine

                indentationDepth = indentationDepth -1;
            }
            else {
                // Create File JSON content
                score = getScore(filesToAnalyze, i);
                descriptionScore += score;
                content.append(GenerateFileDescription(fileName, score));
                if(i<filesToAnalyze.size()-1) {
                    // TODO : Push { } in GenerateFileDescription. Keep here only logic for commas,
                    content.append(tabs.repeat(indentationDepth*2)).append("\t\t},\n").append(tabs.repeat(indentationDepth*2)).append("\t\t{\n");
                }
            }
        }
        description.setJsonContent(content.toString());
        description.setScore(descriptionScore);

        return description;
    }

    private int getScore(ArrayList<File> filesToAnalyze, int i) throws IOException {
        int score = 0;
        BufferedReader fileEvaluation = new BufferedReader(new FileReader(filesToAnalyze.get(i).getPath()));
        while(fileEvaluation.readLine() != null){
            score++;
        }
        return score;
    }

    private String GenerateFileDescription(String fileName, int fileScore) {
        String localIndentDepth = tabs.repeat(indentationDepth*2+3);
        return localIndentDepth + "\"path\" : \""+ fileName +"\",\n" +
                localIndentDepth + "\"type\" : \"file\",\n" +
                localIndentDepth + "\"score\" : \""+ fileScore +"\",\n" +
                localIndentDepth + "\"content\" : [\n" +
                localIndentDepth + "\t{\n" +
                localIndentDepth + "\t}\n" +
                localIndentDepth + "]\n";
    }

    private FolderDescription GenerateDirectoryDescription(File directory) throws IOException {
        FolderDescription folderDescription =  GenerateJSONContent(directory.getPath());;
        String description = folderDescription.getJSONContent();
        int folderScore = folderDescription.getScore();
        String fileNameDirectory = directory.getName();

        if(indentationDepth ==0) {
            String filepath = directory.getPath();
            fileNameDirectory = filepath.replace("\\", "/");
        }

        FolderDescription returnedDescription = new FolderDescription();
        String localIndentDepth = tabs.repeat(indentationDepth*2+1);
        String returnedContent = localIndentDepth +"\"path\" : \""+ fileNameDirectory +"\",\n" +
                localIndentDepth +"\"type\" : \"directory\",\n" +
                localIndentDepth +"\"score\" : \"" + folderScore + "\",\n" +
                localIndentDepth +"\"content\" : [\n" +
                localIndentDepth +"\t{\n" +
                 description +
                localIndentDepth + "\t}\n" +
                localIndentDepth + "]\n";

        returnedDescription.setJsonContent(returnedContent);
        returnedDescription.setScore(folderScore);
        return returnedDescription;
    }
}
