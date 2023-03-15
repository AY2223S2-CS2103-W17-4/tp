package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyShop;

/**
 * A class to access Shop data stored as a json file on the hard disk.
 */
public class JsonShopStorage implements ShopStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonShopStorage.class);

    private Path filePath;

    public JsonShopStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getShopFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyShop> readShop() throws DataConversionException {
        return readShop(filePath);
    }

    /**
     * Similar to {@link #readShop()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyShop> readShop(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableShop> jsonShop = JsonUtil.readJsonFile(
                filePath, JsonSerializableShop.class);
        if (!jsonShop.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonShop.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveShop(ReadOnlyShop shop) throws IOException {
        saveShop(shop, filePath);
    }

    /**
     * Similar to {@link #saveShop(ReadOnlyShop)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveShop(ReadOnlyShop shop, Path filePath) throws IOException {
        requireNonNull(shop);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableShop(shop), filePath);
    }

}
