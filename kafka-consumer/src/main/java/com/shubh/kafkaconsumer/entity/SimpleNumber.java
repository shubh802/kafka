package com.shubh.kafkaconsumer.entity;

public class SimpleNumber {

	private int number;

	public SimpleNumber(int number) {
		super();
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public SimpleNumber() {

	}

	@Override
	public String toString() {
		return "SimpleNumber [number=" + number + "]";
	}

}
