package com.example.elasticsearchclient.model.index;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
//@JsonIgnoreProperties(ignoreUnknown = true)
public class IndexDescription {


    private Map<String,Object> aliases;
    private Map<String, Mappings> mappings;
    private Settings settings;


    public Map<String,Object> getAliases() {
        return aliases;
    }

    public void setAliases(Map<String,Object> aliases) {
        this.aliases = aliases;
    }

    public Map<String, Mappings> getMappings() {
        return mappings;
    }

    public void setMappings(Map<String, Mappings> mappings) {
        this.mappings = mappings;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "IndexDescription{" +
                "aliases=" + aliases +
                ", mappings=" + mappings +
                ", settings=" + settings +
                '}';
    }

}
