package com.accesoriosdm.inventory.exception.dto;

import java.util.List;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        String timestamp,
        String traceId,
        List<Object> details
) {}
