package com.luismiguel.literalura.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "livros")
@Data
public class Livro {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @ManyToOne
  private List<Autor> authors;

  private List<String> languages;
  private Double download_count;

  public Livro(String title, List<Autor> author, Double downloads, List<String> languages) {
    this.title = title;
    this.authors = authors;
    this.download_count = download_count;
    this.languages = languages;
  }
}