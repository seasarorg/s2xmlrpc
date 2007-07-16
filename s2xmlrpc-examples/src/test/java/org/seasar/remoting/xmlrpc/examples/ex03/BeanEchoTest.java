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
package org.seasar.remoting.xmlrpc.examples.ex03;

import org.seasar.extension.unit.S2TestCase;

/**
 * @author takanori
 */
public class BeanEchoTest extends S2TestCase {
    public BeanEchoTest(String name) {
        super(name);
    }

    public void setUp() {
        include("BeanEchoTest.dicon");
    }

    public void test() {
        EchoDto expected = new EchoDto();
        expected.setStrParam("echo");

        BeanEcho beanEcho = (BeanEcho) getComponent(BeanEcho.class);
        EchoDto actual = beanEcho.echo(expected);
        
        assertEquals(expected.getStrParam(), actual.getStrParam());
        assertEquals(expected.getShortParam(), actual.getShortParam());
        assertEquals(expected.getIntParam(), actual.getIntParam());
        assertEquals(expected.getLongParam(), actual.getLongParam());
        assertEquals(new Float(expected.getFloatParam()), new Float(actual.getFloatParam()));
        assertEquals(new Double(expected.getDoubleParam()), new Double(actual.getDoubleParam()));
        assertEquals(expected.isBoolParam(), actual.isBoolParam());
        assertEquals(expected.getCal(), actual.getCal());
    }
}
