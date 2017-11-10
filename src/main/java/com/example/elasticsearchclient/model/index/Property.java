package com.example.elasticsearchclient.model.index;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Map;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Property {

    private String type;
    private Map<String, Property> fields;

    @Override
    public String toString() {
        return "Property{" +
                ", type='" + type + '\'' +
                ", fields=" + fields +
                '}';
    }
}
