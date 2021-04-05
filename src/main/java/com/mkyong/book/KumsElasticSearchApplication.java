package com.mkyong.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.mkyong.book.model.Book;
import com.mkyong.book.service.BookService;

@SpringBootApplication
public class KumsElasticSearchApplication  {

	@Autowired
	private BookService bookService;

	public static void main(String[] args) {
		SpringApplication.run(KumsElasticSearchApplication.class, args);
	}

	
	public void run(String... args) throws Exception {
		bookService.save(new Book("1011", "Elasticsearch Basics", "Rambabu Posa", "23-FEB-2017"));
		bookService.save(new Book("1012", "Apache Lucene Basics", "Rambabu Posa", "13-MAR-2017"));
		bookService.save(new Book("1013", "Apache Solr Basics", "Rambabu Posa", "21-MAR-2017"));

		// fuzzey search
		Page<Book> books = bookService.findByAuthor("Rambabu", new PageRequest(0, 10));

		// List<Book> books = bookService.findByTitle("Elasticsearch Basics");

		books.forEach(x -> System.out.println(x));

	}

}
