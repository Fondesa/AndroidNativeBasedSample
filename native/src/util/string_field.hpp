#pragma once

#include <jni.h>

namespace Jni {

class StringField {
   public:
    const char *utfValue;

    StringField(JNIEnv *env, jstring jvalue);

    ~StringField();

   private:
    jstring jvalue;
    JNIEnv *env;
};
}