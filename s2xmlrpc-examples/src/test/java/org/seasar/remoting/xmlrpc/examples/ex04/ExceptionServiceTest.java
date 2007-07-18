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
package org.seasar.remoting.xmlrpc.examples.ex04;

import org.seasar.extension.unit.S2TestCase;
import org.seasar.remoting.xmlrpc.S2XmlRpcException;

/**
 * サーバ側で例外発生時のテスト
 * @author agata
 */
public class ExceptionServiceTest extends S2TestCase {

    public void setUp() {
        include("ExceptionServiceTest.dicon");
    }

    public void testThrowExcepion() {
    	ExceptionService service = (ExceptionService) getComponent(ExceptionService.class);
    	try {
        	service.throwException();
        	fail();
    	} catch (S2XmlRpcException ex) {
    		assertEquals("Failed to invoke method throwException in class org.seasar.remoting.xmlrpc.examples.ex04.ExceptionServiceImpl: unsupport method!", 
    				ex.getCause().getMessage());
    	}
    }
}
