/*
 * Copyright 2004-2007 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.remoting.xmlrpc.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletInputStream;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockHttpServletResponseImpl;
import org.seasar.framework.mock.servlet.MockServletConfigImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class S2XmlRpcServletTest extends S2TestCase {

	protected void setUp() throws Exception {
		include("S2XmlRpcServletTest.dicon");
	}
	
	public void testTest() throws Exception {
		S2XmlRpcServlet servlet  = new S2XmlRpcServlet();
		MockServletConfigImpl config = new MockServletConfigImpl();
		servlet.init(config);
		MockServletContextImpl ctx = new MockServletContextImpl("/s2xmlrpc");
		String text = "<methodCall>"
			+ "<methodName>Echo.echo</methodName>"
			+ "<params>"
			+ "<param><value><int>1</int></value></param>"
			+ "<param><value><string>value1</string></value></param>"
			+ "</params>"
			+ "</methodCall>";
		final ByteArrayInputStream in = new ByteArrayInputStream(text.getBytes());
		MockHttpServletRequestImpl req = new MockHttpServletRequestImpl(ctx, "/xmlrpc") {
			public ServletInputStream getInputStream() throws IOException {
				return new ServletInputStream() {
					public int available() throws IOException {
						return in.available();
					}
					public int read(byte[] b) throws IOException {
						return in.read(b);
					}
					public int read(byte[] b, int off, int len) {
						return in.read(b, off, len);
					}
					public synchronized void mark(int readlimit) {
						in.mark(readlimit);
					}
					public void close() throws IOException {
						in.close();
					}
					public long skip(long n) throws IOException {
						return in.skip(n);
					}
					public synchronized void reset() throws IOException {
						in.reset();
					}
					public boolean markSupported() {
						return in.markSupported();
					}
					public int read() throws IOException {
						return in.read();
					}
				};
			}
		};
		req.setCharacterEncoding("UTF-8");
		MockHttpServletResponseImpl res = new MockHttpServletResponseImpl(req);
		servlet.doPost(req, res);
		assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<methodResponse>" +
				"<params>" +
				"<param><value>[id = 1] value1</value></param>" +
				"</params>" +
				"</methodResponse>", new String(res.getResponseBytes()));
	}
}
