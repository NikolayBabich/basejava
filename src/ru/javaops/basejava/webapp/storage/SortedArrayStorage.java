package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes, elements ordered lexicographically by uuid
 */
public final class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void doInsert(int index, Resume resume) {
        int insertIndex = -index - 1;
        System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
        storage[insertIndex] = resume;
    }

    @Override
    protected void doRemove(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }

    /**
     * @param uuid identifier of Resume to be returned
     * @return index of the search key, if it is contained in the array storage;
     *         otherwise, <code>(-(<i>insertion point</i>) - 1)</code>. The <i>insertion point</i> defined
     *         as the point at which the key would be inserted.
     * @see Arrays#binarySearch(Object[], int, int, Object)
     */
    @Override
    protected Integer getSpecificSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
