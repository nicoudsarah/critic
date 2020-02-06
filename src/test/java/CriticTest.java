import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
}
