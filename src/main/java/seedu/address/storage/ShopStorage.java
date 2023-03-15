package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyShop;

/**
 * Represents a storage for {@link seedu.address.model.entity.shop.Shop}.
 */
public interface ShopStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getShopFilePath();

    /**
     * Returns Shop data as a {@link seedu.address.model.ReadOnlyShop}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyShop> readShop() throws DataConversionException, IOException;

    /**
     * @see #getShopFilePath()
     */
    Optional<ReadOnlyShop> readShop(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyShop} to the storage.
     * @param shop cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveShop(ReadOnlyShop shop) throws IOException;

    /**
     * @see #saveShop(ReadOnlyShop)
     */
    void saveShop(ReadOnlyShop shop, Path filePath) throws IOException;

}
