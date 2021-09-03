package com.nayki.analyzer.exceptions;

public class FileParseException extends RuntimeException {

    private static final long serialVersionUID = -8108147025252352418L;

    public FileParseException(String fileName) {
        super(String.format("File %s cannot be parsed", fileName));
    }
}
