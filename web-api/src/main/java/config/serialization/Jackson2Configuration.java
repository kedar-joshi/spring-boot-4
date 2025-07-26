package config.serialization;

import java.util.TimeZone;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.jakarta.xmlbind.JakartaXmlBindAnnotationModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;

/**
 * Provides configuration for JSON and XML serialization with Jackson serializers and respective message converters.
 */
@Configuration
public class Jackson2Configuration
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
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter()
	{
		return new MappingJackson2HttpMessageConverter(createJsonMapper());
	}

	/**
	 * Configures message converter for `application/xml` and equivalent content types.
	 *
	 * @return instance of message converter for XML.
	 */
	@Bean("mappingJackson2XmlHttpMessageConverter")
	public MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter()
	{
		return new MappingJackson2XmlHttpMessageConverter(createXmlMapper());
	}

	/**
	 * Creates instance of {@link JsonMapper} with application defaults.
	 *
	 * @return fully configured instance of {@link JsonMapper}.
	 */
	private static JsonMapper createJsonMapper()
	{
		// Configuring JSON mapper
		return configureMapper(new JsonMapper());
	}

	/**
	 * Creates instance of {@link XmlMapper} with application defaults.
	 *
	 * @return fully configured instance of {@link XmlMapper}.
	 */
	private static XmlMapper createXmlMapper()
	{
		// Configuring XML mapper
		final XmlMapper xmlMapper = configureMapper(new XmlMapper());

		// Enabling serialization to XML
		xmlMapper.registerModule(new JacksonXmlModule());

		return xmlMapper;
	}

	/**
	 * Configures received mapper with application defaults.
	 *
	 * @param mapper {@link JsonMapper} or {@link XmlMapper} instance to be configured.
	 */
	private static <T extends ObjectMapper> T configureMapper(final T mapper)
	{
		// Enabling inclusion of only non-fields in serialized content
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		// Disabling failure generation for unknown properties
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		// Disabling output formatting
		mapper.disable(SerializationFeature.INDENT_OUTPUT);

		/*
		 * Disabling serialization of dates / timestamps as millisecond values.
		 * This effectively enables serialization in standard ISO-8601 format e.g. 2023-12-08T18:01:37.486+05:30.
		 */
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

		// Enabling usage of XML-binding annotations for XML and JSON serialization
		mapper.registerModule(new JakartaXmlBindAnnotationModule());

		// Enabling support for JDK 8 data types, e.g. Optional
		mapper.registerModule(new Jdk8Module());

		// Enabling serialization for classes from 'java.time' package
		mapper.registerModule(new JavaTimeModule());

		// Configuring server timezone as the default when timezone is not provided
		mapper.setTimeZone(TimeZone.getDefault());

		return mapper;
	}
}
