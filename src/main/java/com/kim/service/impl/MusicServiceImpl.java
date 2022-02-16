package com.kim.service.impl;

import com.kim.entity.Music;
import com.kim.mapper.MusicMapper;
import com.kim.service.MusicService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kim_japan
 * @since 2021-11-07
 */
@Service
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements MusicService {

}
