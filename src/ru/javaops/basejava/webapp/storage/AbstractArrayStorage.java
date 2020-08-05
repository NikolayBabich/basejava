package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_STORAGE_SIZE = 10_000;

    protected final Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    protected int size = 0;

    /**
     * Removes all of the Resumes from this storage, all elements
     * of array are set to null
     */
    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @param resume Resume to be saved to this storage, if not present yet
     *               and will not exceed maximum size
     */
    @Override
    public final void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            System.out.printf("Resume #%s already exists in storage.%n", resume.getUuid());
        } else if (size >= MAX_STORAGE_SIZE) {
            System.out.println("Resume storage maximum size has been reached.");
        } else {
            insert(index, resume);
            size++;
        }
    }

    /**
     * @param index specify position in array to insert
     * @param resume Resume to be inserted to this storage
     */
    protected abstract void insert(int index, Resume resume);

    @Override
    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());

        if (index < 0) {
            System.out.printf("Resume #%s is not found in storage.%n", resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    /**
     * @param uuid identifier of Resume to be deleted from this storage, if present
     */
    @Override
    public final void delete(String uuid) {
        int index = findIndex(uuid);

        if (index < 0) {
            System.out.printf("Resume #%s is not found in storage.%n", uuid);
        } else {
            remove(index);
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @param index specify position in array to remove Resume
     */
    protected abstract void remove(int index);

    /**
     * @param uuid identifier of Resume to be returned, if present
     * @return Resume with given uuid, or null if none
     */
    @Override
    public final Resume get(String uuid) {
        int index = findIndex(uuid);

        if (index < 0) {
            System.out.printf("Resume #%s is not found in storage.%n", uuid);
            return null;
        }
        return storage[index];
    }

    /**
     * @param uuid identifier of Resume to be returned
     * @return index in array storage for this Resume, if already present
     * or negative value, if none
     */
    protected abstract int findIndex(String uuid);

    /**
     * @return array, contains only Resumes in this storage (without null)
     */
    @Override
    public final Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return the number of Resumes in this storage
     */
    @Override
    public final int size() {
        return size;
    }
}
