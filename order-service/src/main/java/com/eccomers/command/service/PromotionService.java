package com.eccomers.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eccomers.api.request.PromotionRequest;
import com.eccomers.command.action.PromotionAction;

@Service
public class PromotionService {

	@Autowired
	private PromotionAction promotionAction;
	
	public void createPromotion(PromotionRequest requset) {
		promotionAction.publishToKafka(requset);
	}
	
}
