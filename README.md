# JSON in Java
Lots of these out there but this is special because I made it.
Parses a JSON file (currently represented as a single String) to a `JSONObject`.
It is a little rough and ready at the moment but that will get fixed shortly with time.
This project was originally a proof of concept but it has grown on me recently.

Things on the horizon:

- Better getters and setters for the `JSONObject` and `JSONArray` types
- Path-style mechanism to retrieve from nested objects with a separator. Ex: `obj.get("a/b/c", '/');`
- Better constructors (builders?)
- Unified, single Exception type
- Better error messages and information in Tokens
- Verification for `JSONString` contents
- Character iterator for Tokenizer (maybe)
- Multi-line/stream JSON data inputs
- Parser and Verifier combined
- Updated package structure
- Expanded unit tests
- Support for [JSON schema](https://json-schema.org/)
