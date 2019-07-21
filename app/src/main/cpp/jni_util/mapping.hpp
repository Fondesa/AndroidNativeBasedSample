#pragma once

#include <jni.h>

namespace jni {

template<typename T>
jobject mapFromNative(JNIEnv *env, T obj, jclass cls, jmethodID constructor);

template<typename T>
T mapToNative(JNIEnv *env, jobject obj);
}