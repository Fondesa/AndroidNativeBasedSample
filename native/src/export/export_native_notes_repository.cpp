#include <jni.h>
#include "util/mapping.hpp"
#include "util/jclass_util.hpp"
#include "note/note_class_util.hpp"
#include "database_notes_repository.hpp"
#include "database_client.hpp"
#include "util/pointer_wrapper.hpp"
#include "log/log.hpp"

extern "C" {

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

    jclass noteClass = Jni::findClass(env, Jni::Note::cls());
    jmethodID noteConstructorId = Jni::findConstructor(env, noteClass, Jni::Note::ctor());

    auto mapper = [&](Note note) {
        return Jni::mapFromNative<Note>(env, note, noteClass, noteConstructorId);
    };
    return Jni::jArrayFromVector<Note>(env, noteClass, notes, mapper);
}
}