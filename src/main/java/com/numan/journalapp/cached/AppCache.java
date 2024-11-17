package com.numan.journalapp.cached;

import com.numan.journalapp.entity.AppConfigEntity;
import com.numan.journalapp.repo.AppConfigRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppCache {

  public enum AppConfigKeys{
    WEATHER_API;
  }

  private final AppConfigRepo appConfigRepo;

  public final Map<String, String> AppConfigCache = new HashMap<>();

  @PostConstruct
  public void init() {
    List<AppConfigEntity> appConfigEntities = appConfigRepo.findAll();

    for (AppConfigEntity appConfigEntity : appConfigEntities) {
      AppConfigCache.put(appConfigEntity.getKey(), appConfigEntity.getValue());
    }

  }
}
