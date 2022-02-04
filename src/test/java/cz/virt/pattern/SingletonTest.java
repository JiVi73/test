package cz.virt.pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SingletonTest {

    @Test
    void testSingleton__ResultOK() {
        Singleton singleton1 = Singleton.getInstance();
        Singleton singleton2 = Singleton.getInstance();

        Assertions.assertEquals(singleton1, singleton2);

    }

}
