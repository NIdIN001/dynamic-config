package com.company.dynamic_config.reader;

import com.company.exception.DynamicConfigException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;

public class YmlConfigReader implements ConfigReader {

    private final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    @Override
    public <T> T reloadConfig(File dynamicConfigFile, Class<T> type) throws DynamicConfigException {
        try {
            return mapper.readValue(dynamicConfigFile, type);
        } catch (Exception e) {
            throw new DynamicConfigException("Файл конфигурации не существует", e);
        }
    }
}
