#include <jni.h>
#include <note/draft_class_util.hpp>
#include "util/mapping.hpp"
#include "util/jclass_util.hpp"
#include "note/note_class_util.hpp"
#include "notes_interactor_factory.hpp"
#include "database_client.hpp"
#include "util/pointer_wrapper.hpp"
#include "log/log.hpp"

extern "C" {

JNIEXPORT jlong JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_getRepositoryHandle(
    JNIEnv *env,
    jobject /* this */
) {
    auto interactor = NotesInteractorFactory::create();
    return Jni::PointerWrapper<NotesInteractor>::create(interactor)->address();
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_insertNote(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jobject jDraft
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    auto draft = Jni::mapToNative<Draft>(env, jDraft);
    repository->insertNote(draft);

    log::debug("Insert note");
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_deleteNote(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint id
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    repository->deleteNote(id);
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_updateNote(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint noteId,
    jobject jDraft
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    auto draft = Jni::mapToNative<Draft>(env, jDraft);
    repository->updateNote(noteId, draft);

    log::debug("Update note");
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_updateNewDraftTitle(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jstring jTitle
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    auto stringField = Jni::StringField(env, jTitle);
    repository->updateNewDraftTitle(std::string(stringField.utfValue));
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_updateNewDraftDescription(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jstring jDescription
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    auto stringField = Jni::StringField(env, jDescription);
    repository->updateNewDraftDescription(std::string(stringField.utfValue));
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_updateExistingDraftTitle(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint noteId,
    jstring jTitle
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    auto stringField = Jni::StringField(env, jTitle);
    repository->updateExistingDraftTitle(noteId, std::string(stringField.utfValue));
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_updateExistingDraftDescription(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint noteId,
    jstring jDescription
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    auto stringField = Jni::StringField(env, jDescription);
    repository->updateExistingDraftDescription(noteId, std::string(stringField.utfValue));
}

JNIEXPORT jobjectArray JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_getAllNotes(
    JNIEnv *env,
    jobject /* this */,
    jlong handle
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    auto notes = repository->getAllNotes();

    jclass noteClass = Jni::findClass(env, Jni::Note::cls());
    jmethodID noteConstructorId = Jni::findConstructor(env, noteClass, Jni::Note::ctor());

    auto mapper = [&](Note note) {
        return Jni::mapFromNative<Note>(env, note, noteClass, noteConstructorId);
    };
    return Jni::jArrayFromVector<Note>(env, noteClass, notes, mapper);
}

JNIEXPORT jobject JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_getNewDraft(
    JNIEnv *env,
    jobject /* this */,
    jlong handle
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    auto draft = repository->getNewDraft();
    if (draft) {
        jclass cls = Jni::findClass(env, Jni::Draft::cls());
        jmethodID ctor = Jni::findConstructor(env, cls, Jni::Draft::ctor());
        return Jni::mapFromNative<Draft>(env, draft.value(), cls, ctor);
    }
    return nullptr;
}

JNIEXPORT jobject JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_getExistingDraft(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint noteId
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    auto draft = repository->getExistingDraft(noteId);
    if (draft) {
        jclass cls = Jni::findClass(env, Jni::Draft::cls());
        jmethodID ctor = Jni::findConstructor(env, cls, Jni::Draft::ctor());
        return Jni::mapFromNative<Draft>(env, draft.value(), cls, ctor);
    }
    return nullptr;
}

JNIEXPORT void JNICALL
Java_com_fondesa_notes_notes_impl_NativeNotesInteractor_persistChanges(
    JNIEnv *env,
    jobject /* this */,
    jlong handle
) {
    auto repository = Jni::PointerWrapper<NotesInteractor>::get(handle);

    repository->persistChanges();
}
}