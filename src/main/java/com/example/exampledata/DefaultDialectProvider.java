package com.example.exampledata;

import java.sql.Connection;
import java.sql.SQLException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jdbc.repository.config.DialectResolver;
import org.springframework.data.relational.core.dialect.Dialect;
import org.springframework.lang.Nullable;

@Slf4j
class DefaultDialectProvider extends DialectResolver.DefaultDialectProvider {

	@Nullable
	private static Dialect getDialect(Connection connection) throws SQLException {
		return CustomDialect.INSTANCE;
	}
}