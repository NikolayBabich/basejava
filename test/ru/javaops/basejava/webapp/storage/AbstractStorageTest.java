package ru.javaops.basejava.webapp.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javaops.basejava.webapp.Config;
import ru.javaops.basejava.webapp.exception.ExistStorageException;
import ru.javaops.basejava.webapp.exception.NotExistStorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    static final File STORAGE_DIR = Config.get().getStorageDir();

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();

//    private static final Resume RESUME_1 = getTestResume(UUID_1, "Zapp Brannigan");
//    private static final Resume RESUME_2 = getTestResume(UUID_2, "Amy Kroker");
//    private static final Resume RESUME_3 = getTestResume("Zapp Brannigan");
//    private static final Resume RESUME_4 = getTestResume(UUID_2, "Turanga Leela");

    private static final Resume RESUME_1 = new Resume(UUID_1, "Zapp Brannigan");
    private static final Resume RESUME_2 = new Resume(UUID_2, "Amy Kroker");
    private static final Resume RESUME_3 = new Resume("Zapp Brannigan");
    private static final Resume RESUME_4 = new Resume(UUID_2, "Turanga Leela");

    final Storage storage;

    AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public final void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public final void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public final void save() {
        int size = storage.size();
//        Resume newResume = getTestResume("Turanga Leela");
        Resume newResume = new Resume("Turanga Leela");
        storage.save(newResume);
        assertSize(size + 1);
        assertGet(newResume);
    }

    @Test(expected = ExistStorageException.class)
    public final void saveExist() {
        storage.save(RESUME_4);
    }

    @Test
    public final void update() {
        storage.update(RESUME_4);
        assertGet(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public final void updateNotExist() {
//        storage.update(getTestResume("dummy"));
        storage.update(new Resume("dummy"));
    }

    @Test(expected = NotExistStorageException.class)
    public final void delete() {
        int size = storage.size();
        storage.delete(UUID_1);
        assertSize(size - 1);
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public final void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public final void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public final void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public final void getAllSorted() {
        List<Resume> expected = Arrays.asList(RESUME_2, RESUME_3, RESUME_1);
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }

    @Test
    public final void size() {
        assertSize(3);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}
