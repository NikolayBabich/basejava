package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes, elements order is not guaranteed
 */
public final class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(int index, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void remove(int index) {
        storage[index] = storage[size - 1];
    }

    /**
     * @param uuid identifier of Resume to be returned
     * @return index in array storage for this Resume, or -1 if none
     */
    @Override
    protected int searchByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
