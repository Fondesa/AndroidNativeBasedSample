#include <jni.h>
#include <string>
#include "database_note_repository.hpp"
#include "database.hpp"
#include "database_factory.hpp"
#include "foo.hpp"
#include "log/log.hpp"
#include "jni_util/jclass_util.hpp"
#include "jni_util/mapping.hpp"
#include "jni_util/pointer_wrapper.hpp"

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
    jobject /* this */,
    jstring dbPath
) {
    jboolean isCopy = JNI_TRUE;
    const char *utfDbPath = env->GetStringUTFChars(dbPath, &isCopy);
    std::string stdDbPath = std::string(utfDbPath);

    // TODO: move to native
    std::shared_ptr<Database> db = DatabaseFactory::createDatabase(stdDbPath);
    auto createTableStmt = db->createStatement("CREATE TABLE IF NOT EXISTS notes ("
                                               "title TEXT NOT NULL, "
                                               "description TEXT NOT NULL"
                                               ")");
    createTableStmt->execute<void>();

    return jni::PointerWrapper<DatabaseNoteRepository>::make(db)->address();
}

JNIEXPORT void JNICALL
Java_com_fondesa_androidnativebasedsample_NoteRepository_remove(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jint id
) {
    auto repository = jni::PointerWrapper<NoteRepository>::get(handle);

    repository->remove(id);
}

JNIEXPORT void JNICALL
Java_com_fondesa_androidnativebasedsample_NoteRepository_insert(
    JNIEnv *env,
    jobject /* this */,
    jlong handle,
    jobject jDraftNote
) {
    auto repository = jni::PointerWrapper<NoteRepository>::get(handle);

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
    auto repository = jni::PointerWrapper<NoteRepository>::get(handle);

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
    auto repository = jni::PointerWrapper<NoteRepository>::get(handle);

    auto notes = repository->getAll();

    jclass noteClass = jni::findClass(env, "com/fondesa/androidnativebasedsample/Note");
    jmethodID noteConstructorId = jni::findConstructor(env,
                                                       noteClass,
                                                       "(ILjava/lang/String;Ljava/lang/String;)V");

    auto mapper = [&](Note note) {
        return jni::mapFromNative<Note>(env, note, noteClass, noteConstructorId);
    };
    return jni::jArrayFromVector<Note>(env, noteClass, notes, mapper);
}
}