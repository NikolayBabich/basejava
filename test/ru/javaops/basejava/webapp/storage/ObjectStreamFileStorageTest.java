package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.storage.serialization.ObjectStreamStrategy;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStrategy()));
    }
}
