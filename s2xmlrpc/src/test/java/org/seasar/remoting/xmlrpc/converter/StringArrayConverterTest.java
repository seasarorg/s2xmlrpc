package org.seasar.remoting.xmlrpc.converter;

import junit.framework.TestCase;

public class StringArrayConverterTest extends TestCase {

	private StringArrayConverter converter;

	protected void setUp() throws Exception {
		converter = new StringArrayConverter();
	}

	public void testIsConvertable() throws Exception {
		assertTrue(converter.isConvertable(null));
		assertTrue(converter.isConvertable(new Object[] {"one", "two"}));
		assertTrue(converter.isConvertable(new String[] {"one", "two"}));
		assertFalse(converter.isConvertable(new Integer[] {new Integer(1), new Integer(2)}));
		assertFalse(converter.isConvertable(new Object[] {new Integer(1), "two"}));
	}
	
	public void testConvertByNull() throws Exception {
		String[] dest = (String[]) converter.convert(null);
		assertNull(dest);
	}

	public void testConvertByObjectArray() throws Exception {
		String[] dest = (String[]) converter.convert(new Object[] {"one", "two"});
		assertEquals(2, dest.length);
		assertEquals("one", dest[0]);
		assertEquals("two", dest[1]);
	}

	public void testConvertByStringArray() throws Exception {
		String[] dest = (String[]) converter.convert(new String[] {"one", "two"});
		assertEquals(2, dest.length);
		assertEquals("one", dest[0]);
		assertEquals("two", dest[1]);
	}

	public void testConvertInvalidArrayType() throws Exception {
		try {
			converter.convert(new Integer[] {new Integer(1), new Integer(1)});
			fail();
		} catch (IllegalArgumentException ex) {
			
		}
	}

	public void testConvertInvalidElementType() throws Exception {
		try {
			converter.convert(new Object[] {new Integer(1), "two"});
			fail();
		} catch (IllegalArgumentException ex) {
			
		}
	}
}
