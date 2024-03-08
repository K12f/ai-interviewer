package io.github.k12f.aiinterview.infra.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Tumi
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Response<T> {
    private int code;

    private String msg;

    @Nullable
    private T data = null;

    public Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * ok
     */
    public static BodyBuilder ok() {
        return new BodyBuilder(200, "ok");
    }

    public static BodyBuilder failed() {
        return new BodyBuilder(400, "failed");
    }

    public static BodyBuilder failed(String msg) {
        return new BodyBuilder(400, msg);
    }

    public static BodyBuilder failed(int code, String msg) {
        return new BodyBuilder(code, msg);
    }

    public static BodyBuilder build(int code, String msg) {
        return new BodyBuilder(code, msg);
    }

    public static class BodyBuilder {
        private final int code;
        private final String msg;

        public BodyBuilder(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private <T> boolean checkNullOrEmpty(T body) {
            if (null == body || body == "") {
                return true;
            }
            return body instanceof List && ((List<?>) body).isEmpty();
        }

        public final <T> Response<T> body() {
            return new Response<>(this.code, this.msg, null);
        }

        public final <T> Response<T> body(@Nullable T body) {
            if (checkNullOrEmpty(body)) {
                return new Response<>(this.code, this.msg, null);
            }
            return new Response<>(this.code, this.msg, body);
        }
    }
}
