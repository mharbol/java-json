
package io.github.mharbol.json.schema;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import io.github.mharbol.json.JSONObject;
import io.github.mharbol.json.parser.Parser;

/**
 * TestBase
 */
public abstract class TestBase {

    /**
     * Read a test resource file.
     *
     * @param file name of the resource file to read
     * @return the contents of the file as a single {@link String}
     * @throws Exception if the file could not be found or read
     */
    protected String readTestFile(String file) throws Exception {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    /**
     * Read a test resource JSON file.
     *
     * @param file name of the resource file to read
     * @return the contents of the file as a {@link JSONObject}
     * @throws Exception if the file could not be found or read
     */
    protected JSONObject readTestObject(String file) throws Exception {
        return Parser.parseJSON(readTestFile(file));
    }

    /**
     * Read a json schema resource JSON file.
     *
     * @param file the name of the resource schema file to read
     * @return the contents of the file as a {@link JSONSchema}
     * @throws Exception if the file could not be found or read
     */
    protected JSONSchema readSchemaFile(String file) throws Exception {
        return new ObjectSchema(readTestObject(file));
    }
}
