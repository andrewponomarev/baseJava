import static java.util.Objects.requireNonNull;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {

    private static Integer STORAGE_LENGTH = 10000;

    private Resume[] storage = new Resume[STORAGE_LENGTH];

    private  int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (size == storage.length) {
            System.out.println("Хранилище заполнено");
            return;
        }
        if (resumeExists(r.uuid)) {
            System.out.println("Error");
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
        if (id < 0) {
            System.out.println("Error");
            return;
        }
        storage[id] = storage[--size];
        storage[size] = null;
    }

    void update(Resume r) {
        int id = findIndex(r.uuid);
        if (id < 0) {
            System.out.println("Error");
            return;
        }
        storage[id] = r;
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

    private boolean resumeExists(String uuid) {
        return findIndex(uuid) >= 0;
    }

}
