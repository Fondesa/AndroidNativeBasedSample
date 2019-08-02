cmake_minimum_required(VERSION 3.10)

set(PROJECT_NAME notes-native)
get_filename_component(PROJECT_DIR ${CMAKE_CURRENT_SOURCE_DIR} DIRECTORY)

add_library(native_mobile_lib SHARED IMPORTED)
set_target_properties(native_mobile_lib PROPERTIES IMPORTED_LOCATION
        ${PROJECT_DIR}/libs/nativemobile/${ANDROID_ABI}/libnativemobile.so)

set(SOURCE_FILES
        export.cpp
        util/jclass_util.cpp
        util/string_field.cpp
        util/mapping.cpp
        log/log.cpp
        )

add_library(${PROJECT_NAME} SHARED ${SOURCE_FILES})

target_include_directories(${PROJECT_NAME} PRIVATE ${PROJECT_DIR}/libs/nativemobile/include)

target_link_libraries(
        ${PROJECT_NAME}
        native_mobile_lib
        log
)
