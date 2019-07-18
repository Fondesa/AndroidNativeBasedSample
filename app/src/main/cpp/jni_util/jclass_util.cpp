#include "jclass_util.hpp"
#include "../log/log.hpp"

namespace jclass_util {

    StringField::StringField(JNIEnv *env, jstring jvalue) {
        this->env = env;
        this->jvalue = jvalue;
        jboolean isCopy = JNI_TRUE;
        this->utfValue = env->GetStringUTFChars(jvalue, &isCopy);
        log::debug("StringField constructor called with value: " + std::string(utfValue));
    }

    StringField::~StringField() {
        // Release the UTF chars when they aren't used anymore.
        env->ReleaseStringUTFChars(jvalue, utfValue);
        log::debug("StringField destructor called");
    }

    StringField findStringField(JNIEnv *env, jobject obj, jclass cls, const char *fieldName) {
        jfieldID fieldId = env->GetFieldID(cls, fieldName, "Ljava/lang/String;");
        auto jvalue = (jstring) env->GetObjectField(obj, fieldId);
        return StringField(env, jvalue);
    }

    jclass findClass(JNIEnv *env, const char *className) {
        jclass noteClass = env->FindClass(className);
        if (!noteClass) {
            auto msg = std::string("The class \"") + className + "\" doesn't exist.";
            env->ThrowNew(env->FindClass("java/lang/ClassNotFoundException"), msg.c_str());
        }
        // TODO: also called when exc?
        return noteClass;
    }

    jmethodID findMethod(JNIEnv *env, jclass cls, const char* name, const char* signature) {
        jmethodID method = env->GetMethodID(cls, name, signature);
        if (!method) {
            auto msg = std::string("The method \"") + name + "()\" doesn't exist.";
            env->ThrowNew(env->FindClass("java/lang/NoSuchMethodError"), msg.c_str());
        }
        // TODO: also called when exc?
        return method;
    }

    jmethodID findConstructor(JNIEnv *env, jclass cls, const char* signature) {
        return findMethod(env, cls, "<init>", signature);
    }

}