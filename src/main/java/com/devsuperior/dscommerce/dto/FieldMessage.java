package com.devsuperior.dscommerce.dto;

public class FieldMessage  {
	
	private String fieldName;
	private String messege;


	public FieldMessage(String fieldName, String messege) {
		this.fieldName = fieldName;
		this.messege = messege;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getMessege() {
		return messege;
	}

}
