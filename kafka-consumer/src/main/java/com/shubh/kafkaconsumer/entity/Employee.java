package com.shubh.kafkaconsumer.entity;

import java.time.LocalDate;
import com.shubh.kafkaconsumer.json.CustomLocalDateDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Employee {

	@JsonProperty("employee_id")
	private String employeeId;

	private String name;

	@JsonProperty("birth_date")
	@JsonDeserialize(using = CustomLocalDateDeserializer.class)
	private LocalDate birthDate;

	public Employee() {

	}

	public Employee(String employeeId, String name, LocalDate birthDate) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.birthDate = birthDate;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public String getName() {
		return name;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name + ", birthDate=" + birthDate + "]";
	}

}
