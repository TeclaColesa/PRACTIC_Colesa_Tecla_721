package org.example.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.InputStream;
import java.util.List;

public class JsonRepository <T> implements Repository<T> {
    protected List<T> data;

    protected JsonRepository(String filePath, Class<T> clazz) {
        this.data = loadFromJson(filePath, clazz);
    }

    @Override
    public List<T> findAll() {
        return data;
    }


    private List<T> loadFromJson(String filePath, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream is = getClass()
                    .getClassLoader()
                    .getResourceAsStream(filePath);

            CollectionType type = mapper.getTypeFactory()
                    .constructCollectionType(List.class, clazz);

            return mapper.readValue(is, type);

        } catch (Exception e) {
            throw new RuntimeException("cannot load json file: " + filePath , e);
        }
    }
}
