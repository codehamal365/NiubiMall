package com.gl.service.impl;

import com.gl.entity.Teacher;
import com.gl.mapper.TeacherMapper;
import com.gl.service.ITeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师表 服务实现类
 * </p>
 *
 * @author Long
 * @since 2021-12-21
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements ITeacherService {

}
