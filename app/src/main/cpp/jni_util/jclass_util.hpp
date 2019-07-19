#pragma once

#include <jni.h>
#include "string_field.hpp"

namespace jni_util {

    StringField findStringField(JNIEnv *env, jobject obj, jclass cls, const char *fieldName);

    jclass findClass(JNIEnv *env, const char *className);

    jmethodID findMethod(JNIEnv *env, jclass cls, const char* name, const char* signature);

    jmethodID findConstructor(JNIEnv *env, jclass cls, const char* signature);
}