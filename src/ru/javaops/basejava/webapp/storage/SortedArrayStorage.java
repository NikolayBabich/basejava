package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Array based storage for Resumes, elements ordered lexicographically by uuid
 */
public final class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insert(int index, Resume resume) {
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void remove(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    /**
     * @param uuid identifier of Resume to be returned
     * @return index in array storage for this Resume, or negative value if none
     *         otherwise, (-(insertion point) - 1). The insertion point defined as
     *         the point at which the key would be inserted.
     * @see Arrays#binarySearch(Object[], int, int, Object)
     */
    @Override
    protected Integer getSpecificSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getUuid));
    }
}
