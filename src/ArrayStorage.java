/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private static Integer STORAGE_LENGTH = 10000;

    Resume[] storage = new Resume[STORAGE_LENGTH];

    int size = 0;

    void clear() {
        storage = null;
    }

    void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Хранилище заполнено");
            return;
        }
        storage[size++] = r;
    }

    Resume get(String uuid) {
        int id = findIndex(uuid);
        return id < 0 ? null : storage[id];
    }

    void delete(String uuid) {
        int id = findIndex(uuid);
        storage[id] = storage[--size];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] result = new Resume[size];
        if (storage != null) {
            System.arraycopy(storage,0, result, 0, size);
        }
        return result;
    }

    int size() {
        return size;
    }

    private int findIndex(String uuid) {
        int id = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                id = i;
                break;
            }
        }
        return id;
    }

}
