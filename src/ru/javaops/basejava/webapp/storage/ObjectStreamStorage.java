package ru.javaops.basejava.webapp.storage;

import ru.javaops.basejava.webapp.exception.StorageException;
import ru.javaops.basejava.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectStreamStorage extends AbstractFileStorage {
    ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected final void doWrite(Resume resume, OutputStream os) throws IOException {
        try (ObjectOutput oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    protected final Resume doRead(InputStream is) throws IOException {
        try (ObjectInput ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error while deserializing", null, e);
        }
    }
}