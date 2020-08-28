package ru.javaops.basejava.webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_PATH));
        ((PathStorage) storage).setStrategy(new ObjectStreamPathStorage());
    }
}
