package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResultResponse {
    @JsonProperty("header")
    public CommonHeader header;

    @JsonProperty("body")
    public AuthResultResponseBody body;

    public CommonHeader getHeader() {
        return header;
    }

    public void setHeader(CommonHeader header) {
        this.header = header;
    }

    public AuthResultResponseBody getBody() {
        return body;
    }

    public void setBody(AuthResultResponseBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "AuthResultResponse{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
