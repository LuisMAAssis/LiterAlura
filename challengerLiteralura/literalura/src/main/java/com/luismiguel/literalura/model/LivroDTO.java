package com.luismiguel.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO(
    Long id,
    String title,
    List<Autor> authors,
    List<String> languages,
    Double download_count) {
}
