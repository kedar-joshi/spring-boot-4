package config.serialization;

import java.util.TimeZone;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.JacksonJsonHttpMessageConverter;
import org.springframework.http.converter.xml.JacksonXmlHttpMessageConverter;
import tools.jackson.databind.*;
import tools.jackson.databind.cfg.DateTimeFeature;
import tools.jackson.databind.cfg.MapperBuilder;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.dataformat.xml.XmlMapper;
import tools.jackson.dataformat.xml.XmlModule;
import tools.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationModule;

/**
 * Provides configuration for JSON and XML serialization with Jackson serializers and respective message converters.
 */
@Configuration
@ConditionalOnBooleanProperty(name = "application.serialization.jackson3.enabled")
public class JacksonConfiguration
{
	/**
	 * Configures instance of {@link JsonMapper} for JSON serialization.
	 * <p>
	 * The configuration includes -
	 * - Serialization of NULL values
	 * - Formatting of output values
	 * - Required modules
	 *
	 * @return fully configured instance of {@link JsonMapper}.
	 */
	@Bean(name = { "objectMapper", "jsonMapper" })
	public JsonMapper jsonMapper()
	{
		return createJsonMapper();
	}

	/**
	 * Configures instance of {@link XmlMapper} for XML serialization.
	 * <p>
	 * The configuration includes -
	 * - Serialization of NULL values
	 * - Formatting of output values
	 * - Required modules
	 *
	 * @return fully configured instance of {@link XmlMapper}.
	 */
	@Bean(name = "xmlMapper")
	public XmlMapper xmlMapper()
	{
		return createXmlMapper();
	}

	/**
	 * Configures message converter for `application/json` and equivalent content types.
	 *
	 * @return instance of message converter for JSON.
	 */
	@Bean(name = "mappingJackson2HttpMessageConverter")
	public JacksonJsonHttpMessageConverter mappingJackson2HttpMessageConverter()
	{
		return new JacksonJsonHttpMessageConverter(createJsonMapper());
	}

	/**
	 * Configures message converter for `application/xml` and equivalent content types.
	 *
	 * @return instance of message converter for XML.
	 */
	@Bean("mappingJackson2XmlHttpMessageConverter")
	public JacksonXmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter()
	{
		return new JacksonXmlHttpMessageConverter(createXmlMapper());
	}

	/**
	 * Creates instance of {@link JsonMapper} with application defaults.
	 *
	 * @return fully configured instance of {@link JsonMapper}.
	 */
	private static JsonMapper createJsonMapper()
	{
		// Configuring JSON mapper
		return configureMapper(JsonMapper.builder()).build();
	}

	/**
	 * Creates instance of {@link XmlMapper} with application defaults.
	 *
	 * @return fully configured instance of {@link XmlMapper}.
	 */
	private static XmlMapper createXmlMapper()
	{
		// Configuring XML mapper
		final XmlMapper.Builder builder = configureMapper(XmlMapper.builder());

		// Enabling serialization to XML
		builder.addModule(new XmlModule());

		return builder.build();
	}

	/**
	 * Configures received mapper with application defaults.
	 *
	 * @param builder {@link JsonMapper} or {@link XmlMapper} instance to be configured.
	 */
	private static <O extends ObjectMapper, T extends MapperBuilder<O, ?>> T configureMapper(final T builder)
	{
		// Disabling failure generation for unknown properties
		builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		// Disabling output formatting
		builder.disable(SerializationFeature.INDENT_OUTPUT);

		/*
		 * Disabling serialization of dates / timestamps as millisecond values.
		 * This effectively enables serialization in standard ISO-8601 format e.g. 2023-12-08T18:01:37.486+05:30.
		 */
		builder.disable(DateTimeFeature.WRITE_DATES_AS_TIMESTAMPS);

		// Enabling usage of XML-binding annotations for XML and JSON serialization
		builder.addModule(new JakartaXmlBindAnnotationModule());

		// Enabling support for JDK 8 data types, e.g. Optional
		// builder.addModule(new Jdk8Module());

		// Enabling serialization for classes from 'java.time' package
		// builder.addModule(new JavaTimeModule());

		// Configuring server timezone as the default when timezone is not provided
		builder.defaultTimeZone(TimeZone.getDefault());

		return builder;
	}
}
