#pragma once

#include <jni.h>
#include <vector>
#include "string_field.hpp"

namespace Jni {

template<typename T>
T findField(JNIEnv *env, jobject obj, jclass cls, const char *fieldName);

jclass findClass(JNIEnv *env, const char *className);

jmethodID findMethod(JNIEnv *env, jclass cls, const char *name, const char *signature);

jmethodID findConstructor(JNIEnv *env, jclass cls, const char *signature);

template<typename T>
jobjectArray jArrayFromVector(JNIEnv *env,
                              jclass cls,
                              std::vector<T> items,
                              std::function<jobject(T)> mapper);
}