package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Collection;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    static final int STORAGE_LIMIT_SIZE = 10_000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT_SIZE];
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
     * @return the number of Resumes in this storage
     */
    @Override
    public final int size() {
        return size;
    }

    @Override
    protected final void saveImpl(Integer index, Resume resume) {
        if (size >= STORAGE_LIMIT_SIZE) {
            throw new StorageException("Resume storage limit size has been reached",
                    resume.getUuid());
        }
        insert(index, resume);
        size++;
    }

    /**
     * @param index  specify position in array to insert
     * @param resume Resume to be inserted to this storage
     */
    protected abstract void insert(int index, Resume resume);

    @Override
    protected final void updateImpl(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    protected final void deleteImpl(Integer index) {
        remove(index);
        storage[size - 1] = null;
        size--;
    }

    /**
     * @param index specify position in array to remove Resume
     */
    protected abstract void remove(int index);

    @Override
    protected final Resume getImpl(Integer index) {
        return storage[index];
    }

    @Override
    protected boolean isExists(Integer index) {
        return index >= 0;
    }

    @Override
    protected Collection<Resume> getAllResumes() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }
}
