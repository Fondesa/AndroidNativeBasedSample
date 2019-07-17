#include "log.hpp"
#include <android/log.h>

namespace log {

    void debug(std::string msg) {
        __android_log_print(ANDROID_LOG_DEBUG, "[native-log]", "%s", msg.c_str());
    }
}