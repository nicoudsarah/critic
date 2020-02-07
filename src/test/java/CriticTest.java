import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;

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
        String expectedCriticJSON = "test/samples/EmptyRepository/expected.critic.json";

        RemoveFile(outputCriticJSON);

        Critic critic = new Critic(emptyRepositoryPath);
        Assertions.assertDoesNotThrow(() -> critic.evaluate()) ;
        File criticPathtoFile = new File(outputCriticJSON);
        assertTrue(criticPathtoFile.exists());
        File expectedCriticFile = new File(expectedCriticJSON);
        assertTrue(FilesContentsAreEquals(expectedCriticJSON, outputCriticJSON));
    }

    private boolean FilesContentsAreEquals(String path1, String path2) {
        File f1 = new File(path1);
        File f2 = new File(path2);
        try {
            byte[] content1 = Files.readAllBytes(f1.toPath());
            byte[] content2 = Files.readAllBytes(f2.toPath());
            return Arrays.equals(content1, content2);
        } catch (Exception e)
        {
            return false;
        }
    }

    private void RemoveFile(String fileName) {
        File f = new File(fileName);
        f.delete();
    }
}
