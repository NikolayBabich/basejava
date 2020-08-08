package ru.javaops.basejava.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    private static final String UUID_1 = "uuidZ";
    private static final String UUID_2 = "uuidA";
    private static final String UUID_3 = "uuidQ";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        int size = storage.size();
        storage.save(new Resume());
        Assert.assertEquals(size + 1, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_2));
        Assert.fail();
    }

    @Test(expected = StorageException.class)
    public void saveOverflowLimit() {
        try {
            while (storage.size() < 10_000) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            Assert.fail();
        }
        storage.save(new Resume());
        Assert.fail();
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1);
        storage.update(resume);
        Assert.assertSame(resume, storage.get(resume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int size = storage.size();
        storage.delete(UUID_1);
        Assert.assertEquals(size - 1, storage.size());
        storage.get(UUID_1);
        Assert.fail();
    }

    @Test
    public void get() {
        Resume resume = new Resume(UUID_2);
        Assert.assertNotSame(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
        Assert.fail();
    }

    @Test
    public void getAll() {
        Assert.assertEquals(3, storage.getAll().length);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }
}