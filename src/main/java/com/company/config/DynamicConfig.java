package com.company.config;

import com.company.config.reader.CfgFileType;
import com.company.config.reader.ConfigReader;
import com.company.config.reader.PropertyConfigReader;
import com.company.exception.DynamicConfigException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DynamicConfig<T> {

    private final DynamicConfigProperties dynamicConfigProperties;
    private final ConfigReader reader = new PropertyConfigReader();

    private File dynamicConfigFile;
    private long lastModified;
    private T model;

    @PostConstruct
    private void initDynamicConfig() throws DynamicConfigException {
        dynamicConfigFile = new File(dynamicConfigProperties.getPath());

        if (!dynamicConfigFile.exists() || !dynamicConfigFile.isFile() || !dynamicConfigFile.canRead()) {
            throw new DynamicConfigException("Файл конфигурации " + dynamicConfigFile.getAbsolutePath() + " не существует");
        }

        String extension = dynamicConfigProperties.getPath().substring(dynamicConfigProperties.getPath().indexOf(".") + 1);
        if (Arrays.stream(CfgFileType.values()).noneMatch(fileType -> fileType.name().equalsIgnoreCase(extension))) {
            throw new DynamicConfigException("Тип файла " + extension + " не поддерживается");
        }
    }

    public T get(Class<T> clazz) throws DynamicConfigException {
        long lm = dynamicConfigFile.lastModified();
        if (dynamicConfigFile.lastModified() > lastModified) {
            model = reader.reloadConfig(dynamicConfigFile, clazz);
            lastModified = dynamicConfigFile.lastModified();
        }

        return model;
    }
}
