# JSON for Java
Lots of these out there but this is special because I made it.
Parses a JSON file (currently represented as a single String) to a `JSONObject`.
Still largely in progress.

Things on the horizon:

- Support for [JSON schema](https://json-schema.org/); ongoing
- `JSONString` matching full JSON spec (with unicode)
- Better constructors (builder pattern)
- Multi-line/stream JSON data inputs
- Parser and Verifier combined with iterator
- Better getters and setters for the `JSONObject` and `JSONArray` types
- Expanded unit tests
- JSONPath query for `JSONObject` in accordance with RFC 9535.
- Better error messages and information in Tokens
- Unified, single Exception type
