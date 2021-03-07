package com.sip.bms.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table (name = "myrole")
public class Role {

	
	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + name + "]";
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name= "role_id")
	private int id;
	
	@NotBlank(message = "Category Name is mandatory")
	@Column(name= "name")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Role(String name) {
		this.name = name;
	}
	public Role() {}
	
}
