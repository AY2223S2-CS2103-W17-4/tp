package seedu.address.model;

import java.util.List;

/**
 * Any class implementing this interface can be found with FindCommand
 */
public interface Findable {

    /**
     * Checks if any of the class's attributes match a specific keyword
     *
     * @param keyword Keyword to check against
     */
    boolean hasKeyword(String keyword);

    /**
     * Checks if the contents of this class matches the keywords given
     *
     * @param keywords List of keywords
     */
    default boolean hasKeywords(List<String> keywords) {
        return keywords.stream()
            .map(String::toLowerCase)
            .allMatch(this::hasKeyword);
    }
}
