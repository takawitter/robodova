package jp.takawitter.robodova.cordova;

import org.robovm.rt.bro.ValuedEnum;

public enum CDVCommandStatus implements ValuedEnum {
/*
    CDVCommandStatus_NO_RESULT = 0,
    CDVCommandStatus_OK,
    CDVCommandStatus_CLASS_NOT_FOUND_EXCEPTION,
    CDVCommandStatus_ILLEGAL_ACCESS_EXCEPTION,
    CDVCommandStatus_INSTANTIATION_EXCEPTION,
    CDVCommandStatus_MALFORMED_URL_EXCEPTION,
    CDVCommandStatus_IO_EXCEPTION,
    CDVCommandStatus_INVALID_ACTION,
    CDVCommandStatus_JSON_EXCEPTION,
    CDVCommandStatus_ERROR

 */
    NoResult(0),
    OK(1),
    ClassNotFoundException(2),
    IllegalAccessException(3),
    InstantiationException(4),
    MalformedUrlException(5),
    IOException(6),
    InvalidAction(7),
    JsonException(8),
    Error(9);

    private final long n;

    private CDVCommandStatus(int n) { this.n = n; }
    public long value() { return n; }
}
