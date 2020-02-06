import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TestBrowser extends BigBrowser{

    @Test
    void TestList() {
        File repertoire = new File("C:\\Users\\Sarah\\IdeaProjects\\critic\\src\\main\\java");
        String liste[] = repertoire.list();
        assertEquals(4, liste.length);
    }


}
