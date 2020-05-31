package com.neu.webapp.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Author {
	@Id
private String name;

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

}
