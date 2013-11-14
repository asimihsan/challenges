class Singleton {
    private static final Singleton instance;
    static {
        try {
            instance = new Singleton();
        } catch (Exception e) {
            throw new RuntimeException("d'ooooohh!!!");
        }
    }
    private Singleton() { }
    public static Singleton getInstance() {
        return instance;
    }
}

class CodingForInterviewsSingleton {
    public static void main(String[] args) {
        //Singleton instance = new Singleton();
        Singleton instance = Singleton.getInstance();
    }
}