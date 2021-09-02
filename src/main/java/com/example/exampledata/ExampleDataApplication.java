package com.example.exampledata;

import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.dialect.AnsiDialect;
import org.springframework.data.relational.core.mapping.NamingStrategy;
import org.springframework.data.relational.core.mapping.RelationalPersistentProperty;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.sql.IdentifierProcessing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@SpringBootApplication
@Slf4j
public class ExampleDataApplication implements CommandLineRunner {

	@Autowired
	private CoolBookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(ExampleDataApplication.class, args);

	}

	@Bean
	public NamingStrategy namingStrategy() {
		return new NamingStrategy() {
			@Override
			public String getColumnName(RelationalPersistentProperty property) {
				return Arrays.stream(NamingStrategy.super.getColumnName(property).split("_"))
						.map(n -> StringUtils.capitalize(n))
						.collect(Collectors.joining(" "));
			}
		};
	}

	@Override
	public void run(String... args) throws Exception {
		CoolBook book = new CoolBook();
		book.setPublishedName("something");
		bookRepository.save(book);
		log.info("count: " + bookRepository.count());
		bookRepository.findAll();
	}
}

@Component
@Primary
class CustomDialect extends AnsiDialect {
	@Override
	public IdentifierProcessing getIdentifierProcessing() {
		return IdentifierProcessing.create(IdentifierProcessing.Quoting.ANSI, IdentifierProcessing.LetterCasing.AS_IS);
	}
}

interface CoolBookRepository extends CrudRepository<CoolBook, Long> {
}

@Table
@Data
class CoolBook {
	@Id
	private Long id;
	private String publishedName;
}