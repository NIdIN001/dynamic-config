package com.company.config.reader;

import com.company.exception.DynamicConfigException;

import java.io.File;

public interface ConfigReader {
    <T> T reloadConfig(File dynamicConfigFile, Class<T> type) throws DynamicConfigException;
}
