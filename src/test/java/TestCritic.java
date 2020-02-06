import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCritic extends Application {

    @Test
    void TestTrue() {
        assertTrue(true);
    }

    @Test
    void TestFalse() {
        assertFalse(false);
    }

    @Test
    void TestIncremenationId(){
        FileEntity file1 = new FileEntity("fifi", 0, Type.folder);
        FileEntity file2 = new FileEntity("dodo", 100, Type.file);
        assertEquals(file1.getId()+1, file2.getId());
    }

    @Test
    void TestName(){
        FileEntity file1 = new FileEntity("fifi", 0, Type.folder);
        assertEquals("fifi", file1.name);
    }

    @Test
    void TestScore(){
        FileEntity file1 = new FileEntity("fifi", 1, Type.folder);
        assertEquals(1, file1.score);
    }

    @Test
    void TestType(){
        FileEntity file1 = new FileEntity("fifi", 0, Type.folder);
        assertEquals(Type.folder, file1.type);
    }
}

