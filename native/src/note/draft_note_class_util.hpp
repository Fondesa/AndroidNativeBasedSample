#pragma once

namespace Jni::DraftNote {

inline const char *const &cls() {
    static const char *value = "com/fondesa/notes/notes/api/DraftNote";
    return value;
}

inline const char *const &ctor() {
    static const char *value = "(Ljava/lang/String;Ljava/lang/String;)V";
    return value;
}
}