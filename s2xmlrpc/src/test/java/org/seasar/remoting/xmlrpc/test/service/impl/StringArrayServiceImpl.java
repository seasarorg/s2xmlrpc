package org.seasar.remoting.xmlrpc.test.service.impl;

import org.seasar.remoting.xmlrpc.test.entity.Entity1;
import org.seasar.remoting.xmlrpc.test.service.StringArrayService;

public class StringArrayServiceImpl implements StringArrayService {
	public String[] echo(String[] text) {
		return text;
	}

	public Entity1 echo2(Entity1 entity) {
		return entity;
	}
}
