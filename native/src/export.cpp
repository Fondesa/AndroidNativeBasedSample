#include <jni.h>
#include <string>
#include "database_notes_repository.hpp"
#include "database_client.hpp"
#include "note_database_initializer.hpp"
#include "foo.hpp"
#include "log/log.hpp"
#include "util/jclass_util.hpp"
#include "util/mapping.hpp"
#include "util/pointer_wrapper.hpp"

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

JNIEXPORT jlong JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesRepository_getRepositoryHandle(
    JNIEnv *env,
    jobject /* this */
) {
    auto db = Db::Client::get();
    return Jni::PointerWrapper<DatabaseNotesRepository>::make(db)->address();
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesRepository_remove(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint id
) {
    auto repository = Jni::PointerWrapper<NotesRepository>::get(handle);

    repository->remove(id);
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesRepository_insert(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jobject jDraftNote
) {
    auto repository = Jni::PointerWrapper<NotesRepository>::get(handle);

    auto draftNote = Jni::mapToNative<DraftNote>(env, jDraftNote);
    repository->insert(draftNote);

    log::debug("Insert note");
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesRepository_update(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint noteId,
    jobject jDraftNote
) {
    auto repository = Jni::PointerWrapper<NotesRepository>::get(handle);

    auto draftNote = Jni::mapToNative<DraftNote>(env, jDraftNote);
    repository->update(noteId, draftNote);

    log::debug("Update note");
}

JNIEXPORT jobjectArray JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesRepository_getAll(
    JNIEnv *env,
    jobject /* this */,
    jlong handle
) {
    auto repository = Jni::PointerWrapper<NotesRepository>::get(handle);

    auto notes = repository->getAll();

    jclass noteClass = Jni::findClass(env, "com/fondesa/notes/notes/api/Note");
    jmethodID noteConstructorId = Jni::findConstructor(env,
                                                       noteClass,
                                                       "(ILjava/lang/String;Ljava/lang/String;)V");

    auto mapper = [&](Note note) {
        return Jni::mapFromNative<Note>(env, note, noteClass, noteConstructorId);
    };
    return Jni::jArrayFromVector<Note>(env, noteClass, notes, mapper);
}
}