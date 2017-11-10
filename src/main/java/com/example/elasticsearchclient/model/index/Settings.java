package com.example.elasticsearchclient.model.index;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Settings {

    private Index index;

    @Override
    public String toString() {
        return "Settings{" +
                "index=" + index +
                '}';
    }
}

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
class Index {
    private String creation_date;
    private String number_of_shards;
    private String number_of_replicas;
    private String uuid;
    private String provided_name;
    private Map<String,Object> version;

    @Override
    public String toString() {
        return "Index{" +
                "creation_date='" + creation_date + '\'' +
                ", number_of_shards='" + number_of_shards + '\'' +
                ", number_of_replicas='" + number_of_replicas + '\'' +
                ", uuid='" + uuid + '\'' +
                ", provided_name='" + provided_name + '\'' +
                ", version=" + version +
                '}';
    }
}