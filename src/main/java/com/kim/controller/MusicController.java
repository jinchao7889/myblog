package com.kim.controller;


import com.kim.common.lang.Result;
import com.kim.entity.Music;
import com.kim.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kim_japan
 * @since 2021-11-07
 */
@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    MusicService musicService;

    @PostMapping("/add")        //添加一条音乐链接
    public Result AddMusicAddress(@Validated @RequestBody Music music){
        boolean add_music_address = musicService.save(music);
        return Result.succ(add_music_address);
    }

    @GetMapping("/list")        //查询所有的音乐链接
    public Result GetMusicAddressList(){
        List<Music> list = musicService.list(null);
        return Result.succ(list);
    }

    @GetMapping("/del/{id}")    //删除选中的音乐链接
    public Result DelMusicAddress(@PathVariable("id") Long id){
        boolean res = musicService.removeById(id);
        return Result.succ(res);
    }


}
