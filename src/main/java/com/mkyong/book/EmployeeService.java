package com.mkyong.book;

import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeService {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@GetMapping("/e1")
	public String saveEmployee(@RequestParam String id, @RequestParam String name) {
		if (!elasticsearchTemplate.indexExists("employeeindex")) {
			elasticsearchTemplate.createIndex(Employee.class);
			elasticsearchTemplate.putMapping(Employee.class);
		}

		Employee e = new Employee();
		e.setEmpid(id);
		e.setName(name);

		// List<IndexQuery> queries = new ArrayList<>();
		IndexQuery indexQuery = new IndexQuery();
		indexQuery.setObject(e);
		indexQuery.setIndexName("employeeindex");
		indexQuery.setType("employeetype");
		elasticsearchTemplate.index(indexQuery);
		elasticsearchTemplate.refresh("employeeindex");
		return "Employee saved...";
	}

	@GetMapping("/searchByKey")
	public List<Employee> getEmployees(@RequestParam String key) {

		
		//should = SQL.OR
		//must = SQL.AND
		//prefixQuery = SQL.LIKE
		//termQuery = SQL =
		//alias = join multiple indices
		NativeSearchQueryBuilder nsqb = new NativeSearchQueryBuilder();
		nsqb.withIndices("employeeindex").withTypes("employeetype");
		final BoolQueryBuilder bq = new BoolQueryBuilder();
//		bq.should(QueryBuilders.prefixQuery("empid", key));
//		bq.should(QueryBuilders.prefixQuery("name", key));
		bq.should(QueryBuilders.termQuery("empid", key));
		bq.should(QueryBuilders.termQuery("name", key));


		NativeSearchQuery nsq = nsqb.withQuery(bq).withPageable(PageRequest.of(0, 100)).build();
		return elasticsearchTemplate.queryForList(nsq, Employee.class);
	}

}
