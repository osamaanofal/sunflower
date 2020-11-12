package com.sunflower.service;

import java.util.List;

import com.sunflower.domain.OnlineClassDBEntity;
import com.sunflower.service.dto.OnlineClassDTO;

/**
 * Service Interface for managing {@link com.sunflower.domain.OnlineClassDBEntity}.
 */
public interface OnlineClassService extends EntityService<OnlineClassDTO, OnlineClassDBEntity>{
    Boolean closeOnlineClass(Long classId) throws Exception;
    
    
    /**
     * availOnlineClass
     *
     * @param classId the id of the entity.
     * @return Boolean
     */
    Boolean availOnlineClass(Long classId) throws Exception;
    
    /**
     * get open courses mapped to class
     *
     * 
     */
    List<OnlineClassDBEntity> getOpenClassesMappedToCourses();
}
