#include <jni.h>
#include <string>
#include "in_memory_note_repository.hpp"
#include "foo.hpp"
#include "log/log.hpp"
#include "jni_util/jclass_util.hpp"
#include "jni_util/mapping.hpp"

extern "C" {
JNIEXPORT jstring JNICALL
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
JNIEXPORT jlong JNICALL
Java_com_fondesa_androidnativebasedsample_NoteRepository_initialize(
    JNIEnv *env,
    jobject /* this */
) {
    auto repository = std::make_unique<InMemoryNoteRepository>();
    // Obtain the stored pointer and release ownership over it.
    auto repositoryHandle = repository.release();
    return reinterpret_cast<jlong>(repositoryHandle);
}

JNIEXPORT void JNICALL
Java_com_fondesa_androidnativebasedsample_NoteRepository_remove(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint id
) {
    auto repository = (InMemoryNoteRepository *) handle;
    repository->remove(id);
}

JNIEXPORT void JNICALL
Java_com_fondesa_androidnativebasedsample_NoteRepository_insert(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jobject jDraftNote
) {
    auto repository = (InMemoryNoteRepository *) handle;

    auto draftNote = jni::mapToNative<DraftNote>(env, jDraftNote);
    repository->insert(draftNote);

    log::debug("Insert note");
}

JNIEXPORT void JNICALL
Java_com_fondesa_androidnativebasedsample_NoteRepository_update(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint noteId,
    jobject jDraftNote
) {
    auto repository = (InMemoryNoteRepository *) handle;

    auto draftNote = jni::mapToNative<DraftNote>(env, jDraftNote);
    repository->update(noteId, draftNote);

    log::debug("Update note");
}

JNIEXPORT jobjectArray JNICALL
Java_com_fondesa_androidnativebasedsample_NoteRepository_getAll(
    JNIEnv *env,
    jobject /* this */,
    jlong handle
) {
    auto repository = (InMemoryNoteRepository *) handle;
    auto notes = repository->getAll();

    jclass noteClass = jni::findClass(env, "com/fondesa/androidnativebasedsample/Note");
    jmethodID noteConstructorId = jni::findConstructor(env,
                                                       noteClass,
                                                       "(ILjava/lang/String;Ljava/lang/String;)V");

    auto mapper = [&](std::shared_ptr<Note> note) {
        return jni::mapFromNative<Note>(env, *note, noteClass, noteConstructorId);
    };
    return jni::jArrayFromVector<std::shared_ptr<Note>>(env, noteClass, notes, mapper);
}
}