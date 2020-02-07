import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CriticTest {
    @Test
    void NonExistantRepositoryCase() {
        String nonExistentRepository = "RepositoryThatDoesNotExist";
        Critic critic = new Critic(nonExistentRepository);
        Assertions.assertThrows(FileNotFoundException.class, () -> {critic.evaluate();});
    }

    @Test
    void RepositoryPathIsRegularFileCase() {
        String repositoryPathIsRegularFile = "test/samples/ExistingRegularFile";
        Critic critic = new Critic(repositoryPathIsRegularFile);
        Assertions.assertThrows(NotDirectoryException.class, () -> {critic.evaluate();});
    }

    @Test
    void EmptyRepositoryCase() {
        String emptyRepositoryPath = "test/samples/EmptyRepository";
        String outputCriticJSON = "test/samples/EmptyRepository/critic.json";

        RemoveFile(outputCriticJSON);

        Critic critic = new Critic(emptyRepositoryPath);
        Assertions.assertDoesNotThrow(() -> critic.evaluate()) ;
        File criticPathtoFile = new File(outputCriticJSON);
        assertTrue(criticPathtoFile.exists());

        RemoveFile(outputCriticJSON);
    }

    private void RemoveFile(String fileName) {
        File f = new File(fileName);
        f.delete();
    }
}
