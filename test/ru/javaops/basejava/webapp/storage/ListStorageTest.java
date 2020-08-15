package ru.javaops.basejava.webapp.storage;

import org.junit.Ignore;
import org.junit.Test;

public class ListStorageTest extends AbstractStorageTest {
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Test
    @Ignore("ListStorage doesn't support overflow limit")
    public void saveOverflowLimit() {
    }
}
