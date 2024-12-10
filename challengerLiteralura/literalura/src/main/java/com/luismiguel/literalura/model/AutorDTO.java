package com.luismiguel.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(
    Long id,
    String name,
    int birth_year,
    int death_year) {
}
