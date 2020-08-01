package ru.javaops.basejava.webapp;

import ru.javaops.basejava.webapp.model.Resume;
import ru.javaops.basejava.webapp.storage.SortedArrayStorage;
import ru.javaops.basejava.webapp.storage.Storage;

/**
 * Test for ArrayStorage implementation
 */
public class MainTestArrayStorage {
//    static final Storage ARRAY_STORAGE = new ArrayStorage();
    static final Storage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuidZ");
        Resume r2 = new Resume();
        r2.setUuid("uuidA");
        Resume r3 = new Resume();
        r3.setUuid("uuidQ");
        Resume r4 = new Resume();
        r4.setUuid("uuidE");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r4);

        System.out.println("\nGet r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println();
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        System.out.println("\nTesting delete");
        printAll();
        ARRAY_STORAGE.delete(r1.getUuid());
        System.out.println();
        printAll();

        System.out.println("\nTesting update");
        Resume r5 = new Resume();
        r5.setUuid("uuidE");
        ARRAY_STORAGE.update(r5);
        printAll();

        System.out.println("\nTesting clear");
        ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("Get all:");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
