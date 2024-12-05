package com.luismiguel.literalura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(String name,
                         int birthYear,
                         int deathYear) {
}
