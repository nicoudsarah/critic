import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

public class Critic {
    private String repositoryPath;

    public Critic(String repositoryPath) {
        this.repositoryPath = repositoryPath;
    }

    public void evaluate() throws FileNotFoundException, NotDirectoryException {
        File repository = new File(repositoryPath);
        if (!repository.exists()) {
            throw new FileNotFoundException();
        }
        else {
            throw new NotDirectoryException(repositoryPath);
        }
    }
}
