public class FileEntity {

    public static int count;
    public int id;
    public String name;
    public int score;
    public Type type;

    public FileEntity(String name, int score, Type type) {
        this.name = name;
        this.score = score;
        this.type = type;
        this.id = count++;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "{ name : \""+ name +"\"," +
                " score : \""+ score +"\"," +
                " type : \""+ type +"\" }";
    }
}
