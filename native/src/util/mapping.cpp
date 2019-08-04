#include <vector>
#include <note.hpp>
#include "draft_note.hpp"
#include "mapping.hpp"
#include "jclass_util.hpp"

namespace Jni {

template<>
jobject mapFromNative<DraftNote>(JNIEnv *env, DraftNote obj, jclass cls, jmethodID constructor) {
    auto draftNote = std::move(obj);
    jstring noteTitle = env->NewStringUTF(draftNote.getTitle().c_str());
    jstring noteDescription = env->NewStringUTF(draftNote.getDescription().c_str());

    return env->NewObject(cls,
                          constructor,
                          noteTitle,
                          noteDescription);
}

template<>
jobject mapFromNative<Note>(JNIEnv *env, Note obj, jclass cls, jmethodID constructor) {
    auto note = std::move(obj);
    jint noteId = note.getId();
    jstring noteTitle = env->NewStringUTF(note.getTitle().c_str());
    jstring noteDescription = env->NewStringUTF(note.getDescription().c_str());

    return env->NewObject(cls,
                          constructor,
                          noteId,
                          noteTitle,
                          noteDescription);
}

template<>
DraftNote mapToNative(JNIEnv *env, jobject obj) {
    jclass cls = env->GetObjectClass(obj);

    auto title = Jni::findField<StringField>(env, obj, cls, "title");
    auto description = Jni::findField<StringField>(env, obj, cls, "description");

    return DraftNote(title.utfValue, description.utfValue);
}

template<>
Note mapToNative(JNIEnv *env, jobject obj) {
    jclass cls = env->GetObjectClass(obj);

    auto id = Jni::findField<int>(env, obj, cls, "id");
    auto title = Jni::findField<StringField>(env, obj, cls, "title");
    auto description = Jni::findField<StringField>(env, obj, cls, "description");

    return Note(id, title.utfValue, description.utfValue);
}
}