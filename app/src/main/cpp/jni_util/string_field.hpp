#pragma once

#include <jni.h>

namespace jni {

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