import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CriticTest {
    @Test
    void NonExistantRepositoryCase() {
        String nonExistentRepository = "RepositoryThatDoesNotExist";
        Critic critic = new Critic(nonExistentRepository);
        Assertions.assertThrows(FileNotFoundException.class, () -> {critic.evaluate();});
    }
}
