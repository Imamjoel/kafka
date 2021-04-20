package com.eccomers.broker.message;

public class PromotionMessage {

	private String promotionCode;
	
	public PromotionMessage() {}

	public PromotionMessage(String promotionCode) {
		super();
		this.promotionCode = promotionCode;
	}

	public String getPromotionCode() {
		return promotionCode;
	}
	
	public void setPromotionCodeString(String promotionCode) {
		this.promotionCode = promotionCode;
	}

	@Override
	public String toString() {
		return "PromotionMessage [promotionCode=" + promotionCode + "]";
	}
	
	
	
}
