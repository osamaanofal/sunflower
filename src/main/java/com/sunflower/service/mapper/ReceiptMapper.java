package com.sunflower.service.mapper;


import com.sunflower.domain.*;
import com.sunflower.service.dto.ReceiptDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ReceiptDBEntity} and its DTO {@link ReceiptDTO}.
 */
@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class})
public interface ReceiptMapper extends EntityMapper<ReceiptDTO, ReceiptDBEntity> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "course.id", target = "courseId")
    ReceiptDTO toDto(ReceiptDBEntity receiptDBEntity);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "courseId", target = "course")
    ReceiptDBEntity toEntity(ReceiptDTO receiptDTO);

    default ReceiptDBEntity fromId(Long id) {
        if (id == null) {
            return null;
        }
        ReceiptDBEntity receiptDBEntity = new ReceiptDBEntity();
        receiptDBEntity.setId(id);
        return receiptDBEntity;
    }
}
