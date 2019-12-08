package com.marcelsouzav.udemy.listener.music.api.gateway.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicJson {

    private String uuid;
    private String path;
    private long size;
}