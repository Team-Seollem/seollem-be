package com.seollem.server.restdocs.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;

public class GsonCustomConfig {

  public Gson gsonBuild() {
    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter());
    return gsonBuilder.create();
  }

}
