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

    StringField getStringField(JNIEnv *env, jobject obj, jclass cls, const char *fieldName) {
        jfieldID fieldId = env->GetFieldID(cls, fieldName, "Ljava/lang/String;");
        auto jvalue = (jstring) env->GetObjectField(obj, fieldId);
        return StringField(env, jvalue);
    }
}