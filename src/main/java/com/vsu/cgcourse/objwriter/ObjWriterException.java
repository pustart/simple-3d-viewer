package com.vsu.cgcourse.objwriter;

class ObjWriterException extends RuntimeException {
    public ObjWriterException(String errorMessage, int lineInd) {
        super("Error writing OBJ file on line: " + lineInd + ". " + errorMessage);
    }
}
