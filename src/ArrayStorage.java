/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int size = 0;

    void clear() {
        if (size > 0) {
            storage = new Resume[10000];
            size = 0;
        }
    }

    void save(Resume r) {
        if (size == storage.length)
            resize(2*storage.length);
        storage[size++] = r;
    }

    Resume get(String uuid) {
        int id = findIndex(uuid);
        if (id < 0) {
            return null;
        }
        return storage[id];
    }

    void delete(String uuid) {
        int id = findIndex(uuid);
        System.arraycopy(storage, id + 1, storage, id, size - id - 1);
        storage[--size] = null;
        if (size > 0 && size == storage.length/4) {
            resize(storage.length/2);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (size == 0) {
            return new Resume[1];
        }
        Resume[] result = new Resume[size];
        System.arraycopy(storage,0, result, 0, size);
        return result;
    }

    int size() {
        return size;
    }

    private int findIndex(String uuid) {
        int id = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid))
                id = i;
        }
        return id;
    }

    private void resize(int max) {
        Resume[] temp = new Resume[max];
        System.arraycopy(storage, 0, temp, 0, storage.length);
        storage = temp;
    }

}
