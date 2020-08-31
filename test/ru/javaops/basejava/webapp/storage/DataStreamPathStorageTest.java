package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serialization.DataStreamStrategy;

public class DataStreamPathStorageTest extends AbstractStorageTest {
    public DataStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new DataStreamStrategy()));
    }
}
