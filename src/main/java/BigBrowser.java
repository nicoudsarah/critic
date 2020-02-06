import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BigBrowser {

    protected static Object main;

    public void Read(){
        File repertoire = new File("C:\\Users\\Sarah\\IdeaProjects\\critic\\src\\main\\java");
        String liste[] = repertoire.list();

        if (liste != null) {
            for (int i = 0; i < liste.length; i++) {
                System.out.println(liste[i]);
            }
        } else {
            System.err.println("Nom de repertoire invalide");
        }
    }
    public static void main(String args[]) {

        File repertoire = new File("C:\\Users\\Sarah\\IdeaProjects\\critic");
        String liste[] = repertoire.list();
        List<File> listeFolder= new ArrayList<File>();
        List<File> listeFile= new ArrayList<File>();
        for (int i = 0; i < liste.length; i++) {
            File superfile=new File(liste[i]);
            //System.out.println(superfile);
            if (superfile.isDirectory()) {
                listeFolder.add(superfile);
            }
            else {
                listeFile.add(superfile);
            }
        }
    }
}
