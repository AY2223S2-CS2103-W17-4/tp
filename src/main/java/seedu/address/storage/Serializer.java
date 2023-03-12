package seedu.address.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * Manages storing of objects.
 *
 * @param <T> Object to be stored.
 * @see Serializable
 */
public class Serializer<T extends Serializable> {
    private static final String DIR = "./data";

    private final Class<T> type;
    private final String filepath;

    private Serializer(Class<T> type, String filepath) {
        this.type = type;
        this.filepath = filepath;

        // Checks if directory exists and if not, creates it.
        File directory = new File(DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }

    }

    /**
     * @param <T>      Type of object to store.
     * @param type     class object for type parameter.
     * @param filename filename for storage file.
     * @return Storage object.
     */
    public static <T extends Serializable> Serializer<T> of(Class<T> type, String filename) {
        String filepath = DIR + '/' + filename;
        return new Serializer<>(type, filepath);
    }

    /**
     * @return Either the object or a StorageError.
     * @throws IOException If error occurred while loading from file
     * @throws ClassCastException If error occurred while casting contents to object
     * @throws ClassNotFoundException If not serialized classes found
     */
    public T load() throws IOException, ClassCastException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filepath));
        Object output = inputStream.readObject();
        inputStream.close();
        return type.cast(output);
    }

    /**
     * Saves object into the file.
     *
     * @param object Object to be saved.
     * @throws IOException If error occurred while saving object
     */
    public void save(T object) throws IOException {
        ObjectOutputStream outputStream;

        outputStream = new ObjectOutputStream(new FileOutputStream(this.filepath));
        outputStream.writeObject(object);
        outputStream.close();
    }

}
