package ru.javaops.basejava.webapp.storage;

import org.junit.Ignore;
import org.junit.Test;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    @Ignore("MapStorage doesn't support overflow limit")
    public void saveOverflowLimit() {
    }
}
