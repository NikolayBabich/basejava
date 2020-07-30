package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int MAX_ARRAY_SIZE = 10000;

    private final Resume[] storage = new Resume[MAX_ARRAY_SIZE];
    private int size = 0;

    /**
     * @param uuid identifier of Resume to be returned
     * @return index in array storage for this Resume, or -1 if none
     */
    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Removes all of the Resumes from this storage, all elements
     * of array are set to null
     */
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @param r Resume to be saved to this storage, if not present yet
     *         and will not exceed size
     */
    public void save(Resume r) {
        if (findIndex(r.getUuid()) == -1) {
            if (size < MAX_ARRAY_SIZE) {
                storage[size++] = r;
            } else {
                System.out.println("Resume storage maximum size is reached.");
            }
        } else {
            System.out.println("Resume already exists in storage.");
        }
    }

    /**
     * @param r Resume to be updated in this storage, if present
     */
    public void update(Resume r) {
        int i = findIndex(r.getUuid());

        if (i >= 0) {
            storage[i] = r;
        } else {
            System.out.println("Resume not found in storage.");
        }
    }

    /**
     * @param uuid identifier of Resume to be returned, if present
     * @return Resume with given uuid, or null if none
     */
    public Resume get(String uuid) {
        int i = findIndex(uuid);

        if (i >= 0) {
            return storage[i];
        } else {
            System.out.println("Resume not found in storage.");
            return null;
        }
    }

    /**
     * @param uuid identifier of Resume to be deleted from this storage, if present
     */
    public void delete(String uuid) {
        int i = findIndex(uuid);

        if (i >= 0) {
            storage[i] = storage[size - 1];
            storage[--size] = null;
        } else {
            System.out.println("Resume not found in storage.");
        }
    }

    /**
     * @return array, contains only Resumes in this storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return the number of Resumes in this storage
     */
    public int size() {
        return size;
    }
}