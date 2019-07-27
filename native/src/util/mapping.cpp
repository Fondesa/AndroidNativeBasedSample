#include <vector>
#include <note.hpp>
#include "draft_note.hpp"
#include "mapping.hpp"
#include "jclass_util.hpp"

namespace Jni {

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
    jclass draftNoteClass = env->GetObjectClass(obj);

    auto title = Jni::findStringField(env, obj, draftNoteClass, "title");
    auto description = Jni::findStringField(env, obj, draftNoteClass, "description");

    return DraftNote(title.utfValue, description.utfValue);
}
}