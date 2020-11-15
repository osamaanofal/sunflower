package com.sunflower.mapper;


import com.sunflower.domain.*;
import com.sunflower.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CourseDBEntity} and its DTO {@link CourseDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseMapper extends EntityMapper<CourseDTO, CourseDBEntity> {


    @Mapping(target = "onlineClasses", ignore = true)
    @Mapping(target = "removeOnlineClass", ignore = true)
    @Mapping(target = "receipts", ignore = true)
    @Mapping(target = "removeReceipt", ignore = true)
    CourseDBEntity toEntity(CourseDTO courseDTO);

    default CourseDBEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        CourseDBEntity courseDBEntity = new CourseDBEntity();
        courseDBEntity.setId(id);
        return courseDBEntity;
    }
}
