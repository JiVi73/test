package cz.virt.pattern;

/**
 * 
 * @author Jirka
 * best version of Singleton
 */
public class Singleton {

    private Singleton() {
    }

    private static class SingletonHelper {
        private static final Singleton singleton = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHelper.singleton;
    }

}
