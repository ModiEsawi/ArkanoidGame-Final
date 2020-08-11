package game;

/**
 * The type Each level set.
 */
public class EachLevelSet {
    private String key;
    private String value;
    private String path;

    /**
     * Sets the key.
     *
     * @param aKey the key
     */
    public void setKey(String aKey) {
        this.key = aKey;
    }

    /**
     * Sets the key value.
     *
     * @param keyValue the value
     */
    public void setValue(String keyValue) {
        this.value = keyValue;
    }

    /**
     * Sets the file path.
     *
     * @param filePath the path
     */
    public void setPath(String filePath) {
        this.path = filePath;
    }

    /**
     * Gets the key.
     *
     * @return the key
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Gets the path.
     *
     * @return the path
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Gets the key value.
     *
     * @return the value
     */
    public String getValue() {
        return this.value;
    }
}
