package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int MAX_STORAGE_SIZE = 10_000;

    protected final Resume[] storage = new Resume[MAX_STORAGE_SIZE];
    protected int size = 0;

    /**
     * @param uuid identifier of Resume to be returned, if present
     * @return Resume with given uuid, or null if none
     */
    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);

        if (index < 0) {
            System.out.printf("Resume #%s is not found in storage.%n", uuid);
            return null;
        }
        return storage[index];
    }

    /**
     * @return the number of Resumes in this storage
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * @param uuid identifier of Resume to be returned
     * @return index in array storage for this Resume, or -1 if none
     */
    protected abstract int findIndex(String uuid);
}
