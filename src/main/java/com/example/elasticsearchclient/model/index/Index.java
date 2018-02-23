package com.example.elasticsearchclient.model.index;

import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Index {
    private String creation_date;
    private String number_of_shards;
    private String number_of_replicas;
    private String uuid;
    private String provided_name;
    private Map<String,Object> version;

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getNumber_of_shards() {
        return number_of_shards;
    }

    public void setNumber_of_shards(String number_of_shards) {
        this.number_of_shards = number_of_shards;
    }

    public String getNumber_of_replicas() {
        return number_of_replicas;
    }

    public void setNumber_of_replicas(String number_of_replicas) {
        this.number_of_replicas = number_of_replicas;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getProvided_name() {
        return provided_name;
    }

    public void setProvided_name(String provided_name) {
        this.provided_name = provided_name;
    }

    public Map<String, Object> getVersion() {
        return version;
    }

    public void setVersion(Map<String, Object> version) {
        this.version = version;
    }

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
