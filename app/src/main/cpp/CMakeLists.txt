cmake_minimum_required(VERSION 3.10)

add_library(native_mobile_lib SHARED IMPORTED)
set_target_properties(native_mobile_lib PROPERTIES IMPORTED_LOCATION
        $ENV{PREBUILT_LIBS}/nativemobile/android/${ANDROID_ABI}/libnativemobile.so)

add_library(jni-wrapper SHARED jni-wrapper.cpp)

target_include_directories(jni-wrapper PRIVATE $ENV{PREBUILT_LIBS}/nativemobile/include)

target_link_libraries(
        jni-wrapper
        native_mobile_lib
)
