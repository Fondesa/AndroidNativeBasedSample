#include <vector>
#include <note.hpp>
#include "draft_note.hpp"
#include "mapping.hpp"
#include "jclass_util.hpp"

namespace jni {

#pragma clang diagnostic push
#pragma ide diagnostic ignored "performance-unnecessary-value-param"
template<>
jobject mapFromNative<Note>(JNIEnv *env, Note obj, jclass cls, jmethodID constructor) {
    jint noteId = obj.getId();
    jstring noteTitle = env->NewStringUTF(obj.getTitle().c_str());
    jstring noteDescription = env->NewStringUTF(obj.getDescription().c_str());

    return env->NewObject(cls,
                          constructor,
                          noteId,
                          noteTitle,
                          noteDescription);
}
#pragma clang diagnostic pop

template<>
DraftNote mapToNative(JNIEnv *env, jobject obj) {
    jclass draftNoteClass = env->GetObjectClass(obj);

    auto title = jni::findStringField(env, obj, draftNoteClass, "title");
    auto description = jni::findStringField(env, obj, draftNoteClass, "description");

    return DraftNote(title.utfValue, description.utfValue);
}
}