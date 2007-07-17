package org.seasar.remoting.xmlrpc.test.service.impl;

import org.seasar.remoting.xmlrpc.test.service.StringArrayService;

public class StringArrayServiceImpl implements StringArrayService {
	public String[] echo(String[] text) {
		return text;
	}
}
