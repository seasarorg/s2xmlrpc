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
package org.seasar.remoting.xmlrpc.examples.common.dto;

public class Department {

    private int    deptno;

    private String dname;

    private String loc;

    public Department() {}

    public int getDeptno() {
        return this.deptno;
    }

    public void setDeptno(int deptno) {
        this.deptno = deptno;
    }

    public java.lang.String getDname() {
        return this.dname;
    }

    public void setDname(java.lang.String dname) {
        this.dname = dname;
    }

    public java.lang.String getLoc() {
        return this.loc;
    }

    public void setLoc(java.lang.String loc) {
        this.loc = loc;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer("[");
        buf.append(deptno).append(", ");
        buf.append(dname).append(", ");
        buf.append("]");
        return buf.toString();
    }

    public int hashCode() {
        return this.getDeptno();
    }
}
