import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @Test
    void RepositoryContainsOneFile () throws IOException {
        String repositoryWithOneFilePath = "test/samples/RepositoryWithOneFile";
        String outputCriticJSON = "test/samples/RepositoryWithOneFile/critic.json";
        String expectedCriticJSON = "test/samples/RepositoryWithOneFile/expected.critic.json";

        RemoveFile(outputCriticJSON);

        Critic critic = new Critic(repositoryWithOneFilePath);
        critic.evaluate() ;
        assertTrue(FilesContentsAreEquals(expectedCriticJSON, outputCriticJSON));
    }

   /* @Test
    void RepositoryContainsTwoFiles () throws IOException {
        String repositoryWithTwoFilesPath = "test/samples/RepositoryWithTwoFiles";
        String outputCriticJSON = "test/samples/RepositoryWithTwoFiles/critic.json";
        String expectedCriticJSON = "test/samples/RepositoryWithTwoFiles/expected.critic.json";

        RemoveFile(outputCriticJSON);

        Critic critic = new Critic(repositoryWithTwoFilesPath);
        critic.evaluate() ;
        assertTrue(FilesContentsAreEquals(expectedCriticJSON, outputCriticJSON));
    }

    @Test
    void RepositoryContainsSubfolderWhichContainsOneFile () throws IOException {
        String RepositoryContainsSubfolderWhichContainsOneFilePath = "test/samples/RepositoryContainsSubfolderWhichContainsOneFile";
        String outputCriticJSON = "test/samples/RepositoryContainsSubfolderWhichContainsOneFile/critic.json";
        String expectedCriticJSON = "test/samples/RepositoryContainsSubfolderWhichContainsOneFile/expected.critic.json";

        RemoveFile(outputCriticJSON);

        Critic critic = new Critic(RepositoryContainsSubfolderWhichContainsOneFilePath);
        critic.evaluate() ;
        assertTrue(FilesContentsAreEquals(expectedCriticJSON, outputCriticJSON));
    }

    @Test
    void RepositoryContainsSubfolderAndOneFile () throws IOException {
        String RepositoryContainsSubfolderAndOneFilePath = "test/samples/RepositoryContainsSubfolderAndOneFile";
        String outputCriticJSON = "test/samples/RepositoryContainsSubfolderAndOneFile/critic.json";
        String expectedCriticJSON = "test/samples/RepositoryContainsSubfolderAndOneFile/expected.critic.json";

        RemoveFile(outputCriticJSON);

        Critic critic = new Critic(RepositoryContainsSubfolderAndOneFilePath);
        critic.evaluate() ;
        assertTrue(FilesContentsAreEquals(expectedCriticJSON, outputCriticJSON));
    }*/

    /*@Test
    void FilesContainsCodeAndAreRelevant() {
        String pathToNonRelevantFile = "test/samples/ExistingRegularFile";
        File nonRelevantFile = new File(pathToNonRelevantFile);
        int startExtensionPosition = pathToNonRelevantFile.lastIndexOf(".");
        assertTrue(startExtensionPosition>0);
        String nonRelevantFileExtension = pathToNonRelevantFile.substring(startExtensionPosition+1);
        assertTrue(FileExtensionIsOK(nonRelevantFileExtension));
    }

    private boolean FileExtensionIsOK(String extensionToTest) {
        String listOfExtensionsAccepted[] = {"java", "txt"};
        boolean extensionMatch = false;
        for (int i = 0; i <= listOfExtensionsAccepted.length-1; i++) {
            if (extensionToTest.equals(listOfExtensionsAccepted[i])) {
                extensionMatch = true ;
            }
        }
        return extensionMatch;
    }*/


}
