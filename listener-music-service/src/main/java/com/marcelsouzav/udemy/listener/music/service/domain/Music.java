package com.marcelsouzav.udemy.listener.music.service.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Music {

    @PrimaryKey
    private UUID id;

    @NotNull
    @NotEmpty
    private String name;

    private String path;

    @NotNull
    @NotEmpty
    private String status;

    @NotNull
    @NotEmpty
    private String uuidCustomer;

    private long size;
}