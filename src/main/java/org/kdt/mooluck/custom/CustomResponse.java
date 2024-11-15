package org.kdt.mooluck.custom;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomResponse {
    private String success;
    private Object response;

    public static CustomResponse success(Object data) {
        if (data == null || (data instanceof Iterable && !((Iterable<?>) data).iterator().hasNext())) {
            return CustomResponse.builder()
                    .success("false")
                    .response(Map.of("message", "비어 있습니다..."))
                    .build();
        }
        return CustomResponse.builder()
                .success("true")
                .response(Map.of("data", data))
                .build();
    }

    public static CustomResponse message(String message) {
        if (message == null || message.isBlank()) {
            return CustomResponse.builder()
                    .success("false")
                    .response(Map.of("message", "비어 있습니다..."))
                    .build();
        }
        return CustomResponse.builder()
                .success("true")
                .response(Map.of("message", message))
                .build();
    }
}
