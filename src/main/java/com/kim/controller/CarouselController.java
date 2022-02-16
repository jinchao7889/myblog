package com.kim.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kim.common.lang.Result;
import com.kim.entity.Carousel;
import com.kim.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kim_japan
 * @since 2021-11-07
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {

    @Autowired
    CarouselService carouselService;

    @PostMapping("/add")     //添加一张轮播图
    public Result AddCarousel(@Validated @RequestBody Carousel carousel){
        boolean res = carouselService.save(carousel);
        return Result.succ(res);
    }


   /* @GetMapping("/getone")  //随机获取一张轮播图
    public Result RandomGetOneCarousel(QueryWrapper<Carousel> carouselQueryWrapper){
        Carousel carousel = carouselService.getOne(carouselQueryWrapper.last("LIMIT 1"));
        return Result.succ(carousel);
    }*/


}
