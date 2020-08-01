package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        int insertionIndex = findIndex(resume.getUuid());

        if (insertionIndex >= 0) {
            System.out.printf("Resume #%s already exists in storage.%n", resume.getUuid());
        } else if (size >= MAX_STORAGE_SIZE) {
            System.out.println("Resume storage maximum size has been reached.");
        } else {
            insertionIndex = -insertionIndex - 1;
            System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
            storage[insertionIndex] = resume;
            size++;
        }
    }

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());

        if (index < 0) {
            System.out.printf("Resume #%s is not found in storage.%n", resume.getUuid());
        } else {
            storage[index] = resume;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);

        if (index < 0) {
            System.out.printf("Resume #%s is not found in storage.%n", uuid);
        } else {
            System.arraycopy(storage, index + 1, storage, index, size - index);
            size--;
        }
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
