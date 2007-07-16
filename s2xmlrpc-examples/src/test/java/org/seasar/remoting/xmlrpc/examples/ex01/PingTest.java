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
package org.seasar.remoting.xmlrpc.examples.ex01;

import org.seasar.extension.unit.S2TestCase;

/**
 * @author takanori
 */
public class PingTest extends S2TestCase {

    public PingTest(String name) {
        super(name);
    }

    public void setUp() {
        include("PingTest.dicon");
    }

    public void testPing() {
        Ping service = (Ping) getComponent(Ping.class);

        try {
            service.ping("ping message");
            
            // Oneway の処理の場合、送信が完了する前に、
            // プロセスが終了しないようにする必要がある。
            Thread.sleep(500);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail();
        }
    }

}
