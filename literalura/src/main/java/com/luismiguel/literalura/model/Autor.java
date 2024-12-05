package com.luismiguel.literalura.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "autores")
@Data
public class Autor {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private int birth_year;
  private int death_year;

  @ManyToOne
  private Livro livro;
}
