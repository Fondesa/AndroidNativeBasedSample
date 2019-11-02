#include <jni.h>
#include <string>
#include "notes.hpp"

extern "C" {

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NotesDatabaseInitializer_initializeDatabase(
    JNIEnv *env,
    jobject /* this */,
    jstring dbPath
) {
    jboolean isCopy = JNI_TRUE;
    const char *utfDbPath = env->GetStringUTFChars(dbPath, &isCopy);
    std::string stdDbPath = std::string(utfDbPath);

    NoteDb::initialize(stdDbPath);
}
}