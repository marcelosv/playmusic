package com.marcelsouzav.udemy.music.add.api.gateway.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcelsouzav.udemy.music.add.api.gateway.json.MusicJson;
import com.marcelsouzav.udemy.music.add.api.service.SaveMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/v1")
public class MusicController {

    @Autowired
    private SaveMusicService saveMusicService;

    @PostMapping("/customers/{uuid}/musics")
    public String create(@PathVariable("uuid") String uuid, @RequestBody MusicJson customerJson) throws ExecutionException, InterruptedException, JsonProcessingException {
        customerJson.setUuidCustomer(uuid);
        return saveMusicService.execute(customerJson);
    }

}