package com.company.controller;

import com.company.dynamic_config.DynamicConfig;
import com.company.exception.DynamicConfigException;
import com.company.model.Model;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ConfigController {

    private final DynamicConfig<Model> dynamicConfig;

    @GetMapping("/config")
    public Model getConfig() throws DynamicConfigException {
        log.info("get request on /config");
        return dynamicConfig.get(Model.class);
    }
}
