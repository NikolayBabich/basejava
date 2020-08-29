package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    static final int STORAGE_LIMIT_SIZE = 1_000;

    final Resume[] storage = new Resume[STORAGE_LIMIT_SIZE];
    int size;

    /**
     * Removes all of the Resumes from this storage, all elements of array are set to null
     */
    @Override
    public final void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return the number of Resumes in this storage
     */
    @Override
    public final int size() {
        return size;
    }

    @Override
    protected final void doSave(Integer index, Resume resume) {
        if (size >= STORAGE_LIMIT_SIZE) {
            throw new StorageException("Resume storage limit size has been reached", resume.getUuid());
        }
        doInsert(index, resume);
        size++;
    }

    /**
     * @param index  specify position in array to doInsert
     * @param resume Resume to be inserted to this storage
     */
    protected abstract void doInsert(int index, Resume resume);

    @Override
    protected final void doUpdate(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected final void doDelete(Integer index) {
        doRemove(index);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @param index specify position in array to doRemove Resume
     */
    protected abstract void doRemove(int index);

    @Override
    protected final Resume doGet(Integer index) {
        return storage[index];
    }

    @Override
    protected final boolean isExists(Integer index) {
        return index >= 0;
    }

    @Override
    protected final List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
