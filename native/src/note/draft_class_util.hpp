#pragma once

namespace Jni::Draft {

inline const char *const &cls() {
    static const char *value = "com/fondesa/notes/notes/api/Draft";
    return value;
}

inline const char *const &ctor() {
    static const char *value = "(Ljava/lang/String;Ljava/lang/String;)V";
    return value;
}
}