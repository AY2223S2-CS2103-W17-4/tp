package seedu.address.model.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import seedu.address.model.service.exception.PartNotFoundException;

/**
 * Mapping between part name and the part in storage
 */
public class PartMap {
    private final Map<String, Part> map;

    public PartMap() {
        this.map = new HashMap<>();
    }

    /**
     * Checks if part is in the list
     *
     * @param partName Part Name
     */
    public boolean contains(String partName) {
        return map.containsKey(partName);
    }

    /**
     * Get part with suitable description
     *
     * @param partName Part name
     * @return part
     * @throws PartNotFoundException If part not in list
     */
    public Part getPart(String partName) throws PartNotFoundException {
        if (!contains(partName)) {
            throw new PartNotFoundException();
        }
        return map.get(partName);
    }

    public Set<Map.Entry<String, Part>> getEntrySet() {
        return map.entrySet();
    }

    /**
     * Adds/Sets part into mapping.
     * <p>
     * NOTE: Could overwrite existing part mapping
     *
     * @param partName Part name
     * @param part     Part
     */
    public void addPart(String partName, Part part) {
        this.map.put(partName, part);
    }

}
