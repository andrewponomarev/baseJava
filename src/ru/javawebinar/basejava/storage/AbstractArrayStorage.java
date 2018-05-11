package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doUpdate(Resume r, Integer key) {
        storage[key] = r;
    }

    @Override
    protected void doSave(Resume r, Integer key) {
        if (size == storage.length) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        addToStorage(r, key);
        size++;
    }

    public int size() {
        return size;
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage[key];
    }

    @Override
    public List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, size));
    }

    @Override
    protected void doDelete(Integer key) {
        size--;
        removeFromStorage(key);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
    }


    protected abstract Integer getKey(String uuid);

    protected abstract void addToStorage(Resume r, int index);

    protected abstract void removeFromStorage(int index);
}