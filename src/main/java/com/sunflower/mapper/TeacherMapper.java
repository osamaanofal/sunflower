package com.sunflower.mapper;


import com.sunflower.domain.*;
import com.sunflower.service.dto.TeacherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TeacherDBEntity} and its DTO {@link TeacherDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TeacherMapper extends EntityMapper<TeacherDTO, TeacherDBEntity> {


    @Mapping(target = "onlineClasses", ignore = true)
    @Mapping(target = "removeOnlineClass", ignore = true)
    TeacherDBEntity toEntity(TeacherDTO teacherDTO);

    default TeacherDBEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        TeacherDBEntity teacherDBEntity = new TeacherDBEntity();
        teacherDBEntity.setId(id);
        return teacherDBEntity;
    }
}
