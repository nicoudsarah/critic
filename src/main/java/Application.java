import java.io.File;

public class Application {

    public static void main(String[] args) {

        FileEntity file1 = new FileEntity("fifi", 0, Type.folder);
        FileEntity file2 = new FileEntity("dodo", 100, Type.file);

        System.out.println(file1.name + "," + file1.getId());
        System.out.println(file2.name + "," + file2.getId());

    }

}
