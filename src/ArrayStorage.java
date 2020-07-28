import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int nextValidId = 0;

    /**
     * Removes all of the Resumes from this storage, all elements of array are set to null
     */
    void clear() {
        Arrays.fill(storage, null);
        nextValidId = 0;
    }

    /**
     * @param r Resume to be saved to this storage
     */
    void save(Resume r) {
        storage[nextValidId] = r;
        nextValidId++;
    }

    /**
     * @param uuid index of Resume to be returned, if present
     * @return Resume with given uuid, or null if none
     */
    Resume get(String uuid) {
        for (int i = 0; i < nextValidId; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    /**
     * @param uuid index of Resume to be deleted from this storage, if present
     */
    void delete(String uuid) {
        for (int i = 0; i < nextValidId; i++) {
            if (uuid.equals(storage[i].uuid)) {
                storage[i] = storage[nextValidId - 1];
                storage[--nextValidId] = null;
            }
        }
    }

    /**
     * @return array, contains only Resumes in this storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, nextValidId);
    }

    /**
     * @return the number of Resumes in this storage
     */
    int size() {
        return nextValidId;
    }
}
