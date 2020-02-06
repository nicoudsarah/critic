import java.io.FileNotFoundException;

public class Critic {
    public Critic(String nonExistentRepository) {
    }

    public void evaluate() throws FileNotFoundException {
        throw new FileNotFoundException();
    }
}
