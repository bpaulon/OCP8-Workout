package ocp.resources;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.junit.Test;
import static org.junit.Assert.*;

public class ResourcesTest {

	@Test
	public void localizedBundleShouldHavePrecedence() {
		Locale locale = new Locale.Builder().setLanguage("it")
				.setRegion("IT")
				.build();
		ResourceBundle bundle = ResourceBundle.getBundle("ocp/resources/messages", locale);
		Enumeration<String> keys = bundle.getKeys();

		assertEquals(Arrays.asList("Cancel", "SI", "NO"), Collections.list(keys)
				.stream()
				.peek(k -> System.out.println(k + ":" + bundle.getString(k)))
				.map(k -> bundle.getString(k))
				.collect(Collectors.toList()));

	}
	
	@Test
	public void canSpecifyBundleUsingDotNotation() {
		ResourceBundle bundle = ResourceBundle.getBundle("ocp.resources.messages");
		assertEquals("NO", bundle.getString("no"));
	}
}
