#include "string_field.hpp"
#include "../log/log.hpp"

namespace Jni {

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
}