#pragma once

#include <jni.h>

namespace jclass_util {

    class StringField {
    public:
        const char *utfValue;

        StringField(JNIEnv *env, jstring jvalue);

        ~StringField();

    private:
        jstring jvalue;
        JNIEnv *env;
    };

    StringField getStringField(JNIEnv *env, jobject obj, jclass cls, const char *fieldName);
}