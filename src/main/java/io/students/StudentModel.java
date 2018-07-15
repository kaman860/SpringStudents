package io.students;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@Table(name="student")
@Entity
public class StudentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	Integer id;
	
	@Column(name="name")
	String name;
	
	@JsonProperty("class")
	@Column(name="class")
	Integer studentClass;
	
	@Column(name="active")
	Boolean active;
	
	@Column(name="admissionyear")
	Integer admissionYear;
	
	public StudentModel() {
		this.active = true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonGetter("class")
	public Integer getStudentClass() {
		return studentClass;
	}

	@JsonSetter("class")
	public void setStudentClass(Integer studentClass) {
		this.studentClass = studentClass;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Integer getAdmissionYear() {
		return admissionYear;
	}

	public void setAdmissionYear(Integer admissionYear) {
		this.admissionYear = admissionYear;
	}
}
