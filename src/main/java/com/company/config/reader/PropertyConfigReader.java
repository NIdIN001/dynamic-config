package com.company.config.reader;

import com.company.exception.DynamicConfigException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyConfigReader implements ConfigReader {

    private final Properties properties = new Properties();
    private final Map<String, String> values = new HashMap<>();

    @Override
    public <T> T reloadConfig(File dynamicConfigFile, Class<T> type) throws DynamicConfigException {
        return parseProperty(dynamicConfigFile, type);
    }

    private <T> T parseProperty(File dynamicConfigFile, Class<T> type) throws DynamicConfigException {
        try (FileInputStream fos = new FileInputStream(dynamicConfigFile)) {
            properties.clear();
            properties.load(fos);
            properties.forEach((key, value) -> values.put(key.toString(), value.toString()));

            T object = type.getDeclaredConstructor().newInstance();
            Field[] fields = type.getDeclaredFields();

            for (Field field : fields) {
                setFieldValue(object, field);
            }

            return object;
        } catch (IOException e) {
            throw new DynamicConfigException("Файл " + dynamicConfigFile.getName() + " не найден", e);
        } catch (Exception e) {
            throw new DynamicConfigException("reflection exception", e);
        }
    }

    private void setFieldValue(Object obj, Field field) throws IllegalAccessException, NoSuchFieldException {
        Field fieldToSet = obj.getClass().getDeclaredField(field.getName());
        fieldToSet.setAccessible(true);
        fieldToSet.set(obj, values.get(field.getName()));
        fieldToSet.setAccessible(false);
    }
}
