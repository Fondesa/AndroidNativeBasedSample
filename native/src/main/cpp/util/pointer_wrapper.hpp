#pragma once

#include <memory>
#include <jni.h>

namespace Jni {

template<typename T>
class PointerWrapper {
   public:
    template<typename ...ARGS>
    static PointerWrapper<T> *make(ARGS... args) {
        return new PointerWrapper<T>(args...);
    }

    static PointerWrapper<T> *create(std::shared_ptr<T> obj) {
        return new PointerWrapper(obj);
    }

    jlong address() const {
        return reinterpret_cast<jlong>(this);
    }

    std::shared_ptr<T> get() const {
        return obj;
    }

    static PointerWrapper<T> *from(jlong handle) {
        return reinterpret_cast<PointerWrapper<T> *>(handle);
    }

    static std::shared_ptr<T> get(jlong handle) {
        return from(handle)->get();
    }

    static void dispose(jlong handle) {
        auto obj = from(handle);
        delete obj;
    }

   private:
    std::shared_ptr<T> obj;

    template<typename ...ARGS>
    explicit PointerWrapper(ARGS... args) {
        obj = std::make_shared<T>(args...);
    }

    explicit PointerWrapper(std::shared_ptr<T> obj) {
        obj = obj;
    }
};
}