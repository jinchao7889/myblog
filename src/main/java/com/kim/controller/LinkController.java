package com.kim.controller;


import com.kim.common.lang.Result;
import com.kim.entity.Link;
import com.kim.service.LinkService;
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
@RequestMapping("/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @PostMapping("/add")        //添加一条友情链接
    public Result AddLink(@Validated @RequestBody Link link){
        boolean add_link = linkService.save(link);
        return Result.succ(add_link);
    }

    @GetMapping("/list")        //查询所有的友情链接记录
    public Result GetLinkList(){
        List<Link> list = linkService.list(null);
        return Result.succ(list);
    }

    @GetMapping("/del/{id}")    //删除友情链接
    public Result DelLink(@PathVariable("id") Long id){
        boolean res = linkService.removeById(id);
        return Result.succ(res);
    }

}
