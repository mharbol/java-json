
package io.github.mharbol.json;

/**
 * JSONString
 */
public class JSONString implements JSONValue {

    /**
     * The {@link String} value of this JSONString as a string recognized by JSON.
     */
    private String value;

    /**
     * @param value String value as a java String
     */
    public JSONString(String value) {
        this.value = stringToJsonString(value);
    }

    // TODO javadoc
    protected static String stringToJsonString(String javaString) {
        // TODO - unicode characters
        // TODO - in testing, figure out what the correct order is for the replaces
        return javaString
                // TODO, this is in the wrong order and maybe we should
                // think about this for replacement
                // .replace("/", "\\/")
                .replace("\b", "\\b")
                .replace("\f", "\\f")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                .replace("\"", "\\\"")
                .replace("\\", "\\\\");
    }

    // TODO javadoc
    protected static String jsonStringToString(String jsonString) {
        // TODO - unicode characters
        // TODO - in testing, figure out what the correct order is for the replaces
        return jsonString
                .replace("\\/", "/")
                .replace("\\b", "\b")
                .replace("\\f", "\f")
                .replace("\\n", "\n")
                .replace("\\r", "\r")
                .replace("\\t", "\t")
                .replace("\\\"", "\"")
                .replace("\\\\", "\\");
    }

    @Override
    public String serialize() {
        return new StringBuilder()
                .append('"')
                .append(this.value)
                .append('"')
                .toString();
    }

    @Override
    public String toString() {
        return jsonStringToString(this.value);
    }

    @Override
    public boolean equals(Object o) {
        // TODO update and test this method
        if (o == this) {
            return true;
        }
        if (null == o || !(o instanceof JSONString)) {
            return false;
        }
        return this.value.equals(((JSONString) o).value);
    }
}
