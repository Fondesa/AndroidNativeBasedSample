#include <jni.h>
#include <in_mem_to_db_draft_notes_repository.hpp>
#include <note/draft_note_class_util.hpp>
#include "util/mapping.hpp"
#include "util/jclass_util.hpp"
#include "database_client.hpp"
#include "util/pointer_wrapper.hpp"

extern "C" {

JNIEXPORT jlong JNICALL
Java_com_fondesa_notes_notes_impl_NativeDraftNotesRepository_getRepositoryHandle(
    JNIEnv *env,
    jobject /* this */
) {
    auto db = Db::Client::get();
    return Jni::PointerWrapper<InMemToDbDraftNotesRepository>::make(db)->address();
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeDraftNotesRepository_beginCreationSession(
    JNIEnv *env,
    jobject /* this */,
    jlong handle
) {
    auto repository = Jni::PointerWrapper<DraftNotesRepository>::get(handle);

    repository->beginCreationSession();
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeDraftNotesRepository_beginUpdateSession(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jobject jNote
) {
    auto repository = Jni::PointerWrapper<DraftNotesRepository>::get(handle);

    auto note = Jni::mapToNative<Note>(env, jNote);
    repository->beginUpdateSession(note);
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeDraftNotesRepository_updateTitle(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jstring jTitle
) {
    auto repository = Jni::PointerWrapper<DraftNotesRepository>::get(handle);

    auto stringField = Jni::StringField(env, jTitle);
    repository->updateTitle(std::string(stringField.utfValue));
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeDraftNotesRepository_updateDescription(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jstring jDescription
) {
    auto repository = Jni::PointerWrapper<DraftNotesRepository>::get(handle);

    auto stringField = Jni::StringField(env, jDescription);
    repository->updateDescription(std::string(stringField.utfValue));
}

JNIEXPORT jobject JNICALL
Java_com_fondesa_notes_notes_impl_NativeDraftNotesRepository_getDraftCreationNote(
    JNIEnv *env,
    jobject /* this */,
    jlong handle
) {
    auto repository = Jni::PointerWrapper<DraftNotesRepository>::get(handle);

    auto draftNote = repository->getDraftCreationNote();
    if (draftNote) {
        jclass cls = Jni::findClass(env, Jni::DraftNote::cls());
        jmethodID ctor = Jni::findConstructor(env, cls, Jni::DraftNote::ctor());
        return Jni::mapFromNative<DraftNote>(env, draftNote.value(), cls, ctor);
    }
    return nullptr;
}

JNIEXPORT jobject JNICALL
Java_com_fondesa_notes_notes_impl_NativeDraftNotesRepository_getDraftUpdateNote(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint noteId
) {
    auto repository = Jni::PointerWrapper<DraftNotesRepository>::get(handle);

    auto draftNote = repository->getDraftUpdateNote(noteId);
    if (draftNote) {
        jclass cls = Jni::findClass(env, Jni::DraftNote::cls());
        jmethodID ctor = Jni::findConstructor(env, cls, Jni::DraftNote::ctor());
        return Jni::mapFromNative<DraftNote>(env, draftNote.value(), cls, ctor);
    }
    return nullptr;
}
}