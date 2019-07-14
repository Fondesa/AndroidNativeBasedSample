cmake_minimum_required(VERSION 3.10)

set(ROOT_PROJECT_DIR ${CMAKE_CURRENT_SOURCE_DIR}/../../../..)

add_library(native_mobile_lib SHARED IMPORTED)
set_target_properties(native_mobile_lib PROPERTIES IMPORTED_LOCATION
        ${ROOT_PROJECT_DIR}/native_libs/nativemobile/${ANDROID_ABI}/libnativemobile.so)

add_library(jni-wrapper SHARED jni-wrapper.cpp)

target_include_directories(jni-wrapper PRIVATE ${ROOT_PROJECT_DIR}/native_libs/nativemobile/include)

target_link_libraries(
        jni-wrapper
        native_mobile_lib
)
