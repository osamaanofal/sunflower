package com.sunflower.web.rest.delegat.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sunflower.service.OnlineClassService;
import com.sunflower.web.rest.OnlineClassesApiDelegate;

@Service
public class OnlineClassesApiDelegateImpl implements OnlineClassesApiDelegate {

	OnlineClassService classService;

	public OnlineClassesApiDelegateImpl(OnlineClassService classService) {
		super();
		this.classService = classService;
	}


	/*
	 * 
	 *  open a Class
	 */
	@Override
	public ResponseEntity<Boolean> availOnlineClass(Long classId) {
		Boolean ok = false;
		try {
			ok = classService.availOnlineClass(classId);
		} catch (Exception e) {

		}

		
		return ok ? ResponseEntity.ok(ok) : ResponseEntity.badRequest().body(ok);
	}

	/*
	 * 
	 * Close A Class
	 */
	@Override
	public ResponseEntity<Boolean> closeOnlineClass(Long classId) {
		Boolean ok = false;
		try {
			ok = classService.closeOnlineClass(classId);
		} catch (Exception e) {

		}

		return ok ? ResponseEntity.ok(ok) : ResponseEntity.badRequest().body(ok);
	}

}
