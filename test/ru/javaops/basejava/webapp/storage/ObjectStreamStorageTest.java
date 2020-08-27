package ru.javaops.basejava.webapp.storage;

public class ObjectStreamStorageTest extends AbstractMapStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}
