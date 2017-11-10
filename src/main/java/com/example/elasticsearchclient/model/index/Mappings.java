package com.example.elasticsearchclient.model.index;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Mappings {

    private Map<String,Object> dynamicTemplates;
    private Map<String,Property> properties;

    @Override
    public String toString() {
        return "Mappings{" +
                "dynamicTemplates=" + dynamicTemplates +
                ", properties=" + properties +
                '}';
    }
}
