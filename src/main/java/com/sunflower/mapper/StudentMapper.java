package com.sunflower.mapper;


import com.sunflower.domain.*;
import com.sunflower.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link StudentDBEntity} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {OnlineClassMapper.class})
public interface StudentMapper extends EntityMapper<StudentDTO, StudentDBEntity> {


    @Mapping(target = "receipts", ignore = true)
    @Mapping(target = "removeReceipt", ignore = true)
    @Mapping(target = "removeOnlineClass", ignore = true)
    StudentDBEntity toEntity(StudentDTO studentDTO);

    default StudentDBEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentDBEntity studentDBEntity = new StudentDBEntity();
        studentDBEntity.setId(id);
        return studentDBEntity;
    }
}
