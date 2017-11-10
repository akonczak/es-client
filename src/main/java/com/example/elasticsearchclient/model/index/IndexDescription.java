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
public class IndexDescription {

    private Map<String,Object> aliases;
    private Map<String, Mappings> mappings;
    private Settings settings;

    @Override
    public String toString() {
        return "IndexDescription{" +
                "aliases=" + aliases +
                ", mappings=" + mappings +
                ", settings=" + settings +
                '}';
    }

}
