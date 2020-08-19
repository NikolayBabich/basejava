package ru.javaops.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Test;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflowLimit() {
        try {
            while (storage.size() < AbstractArrayStorage.STORAGE_LIMIT_SIZE) {
                storage.save(new Resume("Joe Johns"));
            }
        } catch (StorageException e) {
            Assert.fail("Unexpected exception before array is filled");
        }
        storage.save(new Resume("GiveMe Overflow"));
    }
}
