package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based ru.javawebinar.basejava.storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage{

    @Override
    protected Integer getKey(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    protected void addToStorage(Resume r, int index) {
        storage[size] = r;
    }

    @Override
    protected void removeFromStorage(int index) {
        storage[index] = storage[size];
        storage[size] = null;
    }

}
