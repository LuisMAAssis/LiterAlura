package com.luismiguel.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivro(String title,
                         List<Autor> authors,
                         List<String> languages,
                         Double downloads
) {
}
