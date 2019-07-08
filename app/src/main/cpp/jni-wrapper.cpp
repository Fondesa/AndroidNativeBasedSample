#include <jni.h>
#include <string>
#include <iostream>
#include "foo.hpp"

extern "C" JNIEXPORT jstring JNICALL
Java_com_fondesa_androidnativebasedsample_Foo_foo(
        JNIEnv *env,
        jobject /* this */,
        jstring input) {
    jboolean isCopy = JNI_TRUE;
    const char *utfInput = env->GetStringUTFChars(input, &isCopy);
    std::string stdInput = std::string(utfInput);
    std::unique_ptr<Foo> foo = std::make_unique<Foo>(stdInput);
	std::string output = foo->getValue();
    return env->NewStringUTF(output.c_str());
}
