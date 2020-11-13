package com.sunflower.repository.projections.lookup;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface ReceiptNoLookupSelectable {

	@Query("SELECT id as id, receiptNo as name FROM #{#entityName} ")
	 List<EntityLookup> getEntityLookup();
}
