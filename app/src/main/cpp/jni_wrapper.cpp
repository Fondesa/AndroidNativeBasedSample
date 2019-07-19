#include <jni.h>
#include <string>
#include "in_memory_note_repository.hpp"
#include "foo.hpp"
#include "log/log.hpp"
#include "jni_util/jclass_util.hpp"

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
    jclass draftNoteClass = env->GetObjectClass(jDraftNote);

    auto title = jni_util::findStringField(env, jDraftNote, draftNoteClass, "title");
    auto description = jni_util::findStringField(env, jDraftNote, draftNoteClass, "description");

    auto draftNote = std::make_unique<DraftNote>(title.utfValue, description.utfValue);
    auto repository = (InMemoryNoteRepository *) handle;
    repository->insert(*draftNote);

    log::debug("Insert note");
}
JNIEXPORT jobjectArray JNICALL
Java_com_fondesa_androidnativebasedsample_NoteRepository_getAll(
    JNIEnv *env,
    jobject /* this */,
    jlong handle
) {
    auto repository = (InMemoryNoteRepository *) handle;
    auto notes = repository->getAll();

    jclass noteClass = jni_util::findClass(env, "com/fondesa/androidnativebasedsample/Note");
    jmethodID noteConstructorId = jni_util::findConstructor(env,
                                                            noteClass,
                                                            "(ILjava/lang/String;Ljava/lang/String;)V");

    auto mapper = [&](std::shared_ptr<Note> note) {
        jint noteId = note->getId();
        jstring noteTitle = env->NewStringUTF(note->getTitle().c_str());
        jstring noteDescription = env->NewStringUTF(note->getDescription().c_str());

        return env->NewObject(noteClass,
                              noteConstructorId,
                              noteId,
                              noteTitle,
                              noteDescription);
    };
    return jni_util::jArrayFromVector<std::shared_ptr<Note>>(env, noteClass, notes, mapper);
}
}