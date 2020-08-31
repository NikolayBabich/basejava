package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serialization.ObjectStreamStrategy;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStrategy()));
    }
}
