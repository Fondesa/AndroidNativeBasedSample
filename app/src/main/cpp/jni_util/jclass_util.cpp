#include <string>
#include <note.hpp>
#include "jclass_util.hpp"

namespace jni {

StringField findStringField(JNIEnv *env, jobject obj, jclass cls, const char *fieldName) {
    jfieldID fieldId = env->GetFieldID(cls, fieldName, "Ljava/lang/String;");
    auto jvalue = (jstring) env->GetObjectField(obj, fieldId);
    return StringField(env, jvalue);
}

jclass findClass(JNIEnv *env, const char *className) {
    jclass noteClass = env->FindClass(className);
    // TODO: use different null check
    if (!noteClass) {
        auto msg = std::string("The class \"") + className + "\" doesn't exist.";
        env->ThrowNew(env->FindClass("java/lang/ClassNotFoundException"), msg.c_str());
    }
    // TODO: also called when exc?
    return noteClass;
}

jmethodID findMethod(JNIEnv *env, jclass cls, const char *name, const char *signature) {
    jmethodID method = env->GetMethodID(cls, name, signature);
    // TODO: use different null check
    if (!method) {
        auto msg = std::string("The method \"") + name + "()\" doesn't exist.";
        env->ThrowNew(env->FindClass("java/lang/NoSuchMethodError"), msg.c_str());
    }
    // TODO: also called when exc?
    return method;
}

jmethodID findConstructor(JNIEnv *env, jclass cls, const char *signature) {
    return findMethod(env, cls, "<init>", signature);
}

template<typename T>
jobjectArray jArrayFromVector(
    JNIEnv *env,
    jclass cls,
    std::vector<T> items,
    std::function<jobject(T)> mapper
) {
    int size = static_cast<int>(items.size());
    jobjectArray jArray = env->NewObjectArray(size, cls, nullptr);

    for (int i = 0; i < size; i++) {
        auto cItem = items[i];
        auto jItem = mapper(cItem);
        env->SetObjectArrayElement(jArray, i, jItem);
    }
    return jArray;
}
}

/**
 * Dedicated namespace for explicit templates specialization.
 */
namespace jni {

template jobjectArray jArrayFromVector<Note>(
    JNIEnv *env,
    jclass cls,
    std::vector<Note> items,
    std::function<jobject(Note)> mapper
);
}