cmake_minimum_required(VERSION 3.10)

set(PROJECT_NAME notes-native-jni)
get_filename_component(PROJECT_DIR ${CMAKE_CURRENT_SOURCE_DIR} DIRECTORY)

add_library(notes-native-lib SHARED IMPORTED)
set_target_properties(notes-native-lib PROPERTIES IMPORTED_LOCATION
        ${PROJECT_DIR}/libs/notesnative/${ANDROID_ABI}/libnotesnative.so)

set(SOURCE_FILES
        export/export_notes_database_initializer.cpp
        export/export_native_notes_interactor.cpp
        util/jclass_util.cpp
        util/string_field.cpp
        util/mapping.cpp
        log/log.cpp
        )

add_library(${PROJECT_NAME} SHARED ${SOURCE_FILES})

target_include_directories(${PROJECT_NAME}
        PUBLIC ${PROJECT_DIR}/src
        PRIVATE ${PROJECT_DIR}/libs/notesnative/include
        )

target_link_libraries(
        ${PROJECT_NAME}
        notes-native-lib
        log
)
