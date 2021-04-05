package com.kani.book;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

import lombok.Data;

@Data
@Document(indexName = "employeeindex", type = "employeetype")
@Mapping(mappingPath = "index_config/employeeindex/employeetype/mapping.json")
public class Employee {

	private String empid;
	private String name;

}
