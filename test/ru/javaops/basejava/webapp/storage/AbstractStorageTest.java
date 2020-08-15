package ru.javaops.basejava.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    private static final String UUID_1 = "uuidZ";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuidA";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuidQ";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final Resume RESUME_4 = new Resume();

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void save() {
        int size = storage.size();
        storage.save(RESUME_4);
        assertSize(size + 1);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_2));
    }

    @Test
    public void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        assertGet(newResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        int size = storage.size();
        storage.delete(UUID_1);
        assertSize(size - 1);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        assertEquals(3, resumes.length);
        Arrays.sort(resumes);
        assertEquals(RESUME_2, resumes[0]);
        assertEquals(RESUME_3, resumes[1]);
        assertEquals(RESUME_1, resumes[2]);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
