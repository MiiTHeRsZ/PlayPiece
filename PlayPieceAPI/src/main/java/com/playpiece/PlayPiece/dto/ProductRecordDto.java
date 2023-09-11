package com.playpiece.PlayPiece.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record ProductRecordDto(@NotBlank String name, @NotBlank String description, @NotBlank String components, @NotBlank String image01Product, @NotNull BigDecimal value, @NotNull boolean ativo) {
}
