package com.example.elasticsearchclient.model.index;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Field {

    private String type;
    private String ignore_above;

    @Override
    public String toString() {
        return "Field{" +
                "type='" + type + '\'' +
                ", ignore_above='" + ignore_above + '\'' +
                '}';
    }
}
