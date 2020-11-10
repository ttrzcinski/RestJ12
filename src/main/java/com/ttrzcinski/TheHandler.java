package com.ttrzcinski;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

@SuppressWarnings("unused")
public class TheHandler implements RequestHandler<TheHandler.InputObject, String> {

    public String handleRequest(InputObject inputObject, Context context) {
        System.out.printf("Hey, I've got \"%s\" from lambda call.%n", inputObject);

        return "{\"result\": \"Lambda REST Handler in Java 11 says 'got it'.\"}";
    }

    public static class InputObject {
        private String instKey1;
        private String instKey2;
        private String instKey3;

        public String getKey1() {
            return this.instKey1;
        }

        public String getKey2() {
            return this.instKey2;
        }

        public String getKey3() {
            return this.instKey3;
        }

        public void setKey1(String key1) {
            this.instKey1 = key1;
        }

        public void setKey2(String key2) {
            this.instKey2 = key2;
        }

        public void setKey3(String key3) {
            this.instKey3 = key3;
        }

        @Override
        public String toString() {
            return new StringBuilder("InputObject{")
                .append("key1='").append(this.getKey1()).append('\'')
                .append(",key2='").append(this.getKey2()).append('\'')
                .append(",key3='").append(this.getKey3()).append("'}")
                .toString();
        }
    }
}
