package com.jango.laundrysimple.pojo.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response <T> {
    private String message;
    private int code;
    private T data;
    private Boolean status;

    public Response(ResponseBuilder<T> tResponseBuilder) {
        this.message = tResponseBuilder.message;
        this.code = tResponseBuilder.code;
        this.data = tResponseBuilder.data;
        this.status = tResponseBuilder.status;
    }

    public static class ResponseBuilder<T> {
        private String message;
        private int code;
        private T data;
        private Boolean status;

        public ResponseBuilder<T> message(String message) {
            this.message = message;
            return this;
        }
        public ResponseBuilder<T> code(int code) {
            this.code = code;
            return this;
        }
        public ResponseBuilder<T> data(T data) {
            this.data = data;
            return this;
        }
        public ResponseBuilder<T> status(Boolean status) {
            this.status = status;
            return this;
        }

        public Response<T> build() {
            return new Response<>(this);
        }
    }
}
