package com.sunflower.service.mapper;


import com.sunflower.domain.*;
import com.sunflower.service.dto.OnlineClassDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OnlineClassDBEntity} and its DTO {@link OnlineClassDTO}.
 */
@Mapper(componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class})
public interface OnlineClassMapper extends EntityMapper<OnlineClassDTO, OnlineClassDBEntity> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "teacher.id", target = "teacherId")
    OnlineClassDTO toDto(OnlineClassDBEntity onlineClassDBEntity);

    @Mapping(source = "courseId", target = "course")
    @Mapping(source = "teacherId", target = "teacher")
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "removeStudent", ignore = true)
    OnlineClassDBEntity toEntity(OnlineClassDTO onlineClassDTO);

    default OnlineClassDBEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        OnlineClassDBEntity onlineClassDBEntity = new OnlineClassDBEntity();
        onlineClassDBEntity.setId(id);
        return onlineClassDBEntity;
    }
}
