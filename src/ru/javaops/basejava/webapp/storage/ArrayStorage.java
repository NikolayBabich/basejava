package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    /**
     * Removes all of the Resumes from this storage, all elements
     * of array are set to null
     */
    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @param resume Resume to be saved to this storage, if not present yet
     *               and will not exceed maximum size
     */
    @Override
    public void save(Resume resume) {
        if (findIndex(resume.getUuid()) != -1) {
            System.out.printf("Resume #%s already exists in storage.%n", resume.getUuid());
        } else if (size >= MAX_STORAGE_SIZE) {
            System.out.println("Resume storage maximum size has been reached.");
        } else {
            storage[size] = resume;
            size++;
        }
    }

    /**
     * @param resume Resume to be updated in this storage, if present
     */
    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());

        if (index == -1) {
            System.out.printf("Resume #%s is not found in storage.%n", resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    /**
     * @param uuid identifier of Resume to be deleted from this storage, if present
     */
    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);

        if (index == -1) {
            System.out.printf("Resume #%s is not found in storage.%n", uuid);
        } else {
            storage[index] = storage[size - 1];
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in this storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @param uuid identifier of Resume to be returned
     * @return index in array storage for this Resume, or -1 if none
     */
    @Override
    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
