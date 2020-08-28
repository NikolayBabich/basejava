package ru.javaops.basejava.webapp.storage;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR));
        ((FileStorage) storage).setStrategy(new ObjectStreamFileStorage());
    }
}
