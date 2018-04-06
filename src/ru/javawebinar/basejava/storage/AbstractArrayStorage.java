package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume r) {
        requireNonNull(r, "Resume is null");
        int id = getIndex(r.getUuid());
        if (id < 0) {
            System.out.println("Resume " + r.getUuid() + " not exist");
            return;
        }
        storage[id] = r;
    }

    public void save(Resume r) {
        requireNonNull(r, "Resume is null");
        if (size == storage.length) {
            System.out.println("Storage overflow");
            return;
        }
        int index = getIndex(r.getUuid());
        if (index >= 0) {
            System.out.println("Resume " + r.getUuid() + " already exist");
            return;
        }
        addToStorage(r, index);
        size++;
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " not exist");
            return;
        }
        size--;
        removeFromStorage(index);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void addToStorage(Resume r, int index);

    protected abstract void removeFromStorage(int index);
}