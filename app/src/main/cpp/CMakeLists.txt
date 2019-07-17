cmake_minimum_required(VERSION 3.10)

set(ROOT_PROJECT_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../../../..)

add_library(native_mobile_lib SHARED IMPORTED)
set_target_properties(native_mobile_lib PROPERTIES IMPORTED_LOCATION
        ${ROOT_PROJECT_DIR}/native_libs/nativemobile/${ANDROID_ABI}/libnativemobile.so)

set(SOURCE_FILES
        jni_wrapper.cpp
        jni_util/jclass_util.cpp
        log/log.cpp
)
add_library(jni-wrapper SHARED ${SOURCE_FILES})

target_include_directories(jni-wrapper PRIVATE ${ROOT_PROJECT_DIR}/native_libs/nativemobile/include)

target_link_libraries(
        jni-wrapper
        native_mobile_lib
        log
)
