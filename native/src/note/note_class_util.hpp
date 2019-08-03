#pragma once

namespace Jni::Note {

inline const char *const &cls() {
    static const char *value = "com/fondesa/notes/notes/api/Note";
    return value;
}

inline const char *const &ctor() {
    static const char *value = "(ILjava/lang/String;Ljava/lang/String;)V";
    return value;
}
}