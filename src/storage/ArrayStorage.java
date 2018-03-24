package storage;

import model.Resume;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage{

    private static Integer STORAGE_LENGTH = 10000;

    private Resume[] storage = new Resume[STORAGE_LENGTH];

    private  int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        requireNonNull(r, "model.Resume is null");
        if (size == storage.length) {
            System.out.println("Storage overflow");
            return;
        }
        if (findIndex(r.getUuid()) >= 0) {
            System.out.println("model.Resume " + r.getUuid() + " already exist");
            return;
        }
        storage[size++] = r;
    }

    public Resume get(String uuid) {
        int id = findIndex(uuid);
        if (id < 0) {
            System.out.println("model.Resume " + uuid + " not exist");
            return null;
        }
        return storage[id];
    }

    public void delete(String uuid) {
        int id = findIndex(uuid);
        if (id < 0) {
            System.out.println("model.Resume " + uuid + " not exist");
            return;
        }
        storage[id] = storage[--size];
        storage[size] = null;
    }

    public void update(Resume r) {
        requireNonNull(r, "model.Resume is null");
        int id = findIndex(r.getUuid());
        if (id < 0) {
            System.out.println("model.Resume " + r.getUuid() + " not exist");
            return;
        }
        storage[id] = r;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] result = new Resume[size];
        Arrays.copyOfRange(storage, 0, size);
        return result;
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        int id = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                id = i;
                break;
            }
        }
        return id;
    }

}
