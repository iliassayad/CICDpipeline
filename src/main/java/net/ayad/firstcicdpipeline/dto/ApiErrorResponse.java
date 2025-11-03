package net.ayad.firstcicdpipeline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    private LocalDateTime timestamp;
    private String error;
    private String message;
    private String path;
}
