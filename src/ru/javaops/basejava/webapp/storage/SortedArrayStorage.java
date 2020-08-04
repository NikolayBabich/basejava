package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes, elements ordered lexicographically by uuid
 */
public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(int index, Resume resume) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void remove(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }

    /**
     * @param uuid identifier of Resume to be returned
     * @return index in array storage for this Resume, or negative value if none
     *         otherwise, (-(insertion point) - 1). The insertion point defined as
     *         the point at which the key would be inserted.
     * @see Arrays#binarySearch(Object[], int, int, Object)
     */
    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
