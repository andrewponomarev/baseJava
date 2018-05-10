package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        storage[(Integer)key] = r;
    }

    @Override
    protected void doSave(Resume r, Object key) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        addToStorage(r, (Integer)key);
        size++;
    }

    public int size() {
        return size;
    }

    @Override
    protected Resume doGet(Object key) {
        return storage[(Integer) key];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    protected void doDelete(Object key) {
        size--;
        removeFromStorage((Integer) key);
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }


    protected abstract Integer getKey(String uuid);

    protected abstract void addToStorage(Resume r, int index);

    protected abstract void removeFromStorage(int index);
}