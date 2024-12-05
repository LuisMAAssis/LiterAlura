package com.luismiguel.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados{
  @Override
  public <T> T obterDados(String json, Class<T> classe) {
    ObjectMapper mapper = new ObjectMapper();
    try {
      var resultado = mapper.readValue(json, classe);
      return resultado;
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
