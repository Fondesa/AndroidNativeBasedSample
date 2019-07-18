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

    StringField findStringField(JNIEnv *env, jobject obj, jclass cls, const char *fieldName);

    jclass findClass(JNIEnv *env, const char *className);

    jmethodID findMethod(JNIEnv *env, jclass cls, const char* name, const char* signature);

    jmethodID findConstructor(JNIEnv *env, jclass cls, const char* signature);
}