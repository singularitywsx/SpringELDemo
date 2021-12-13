package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonHeader {

	@JsonProperty("Id")
	public String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "CommonHeader{" +
				"id='" + id + '\'' +
				'}';
	}
}
