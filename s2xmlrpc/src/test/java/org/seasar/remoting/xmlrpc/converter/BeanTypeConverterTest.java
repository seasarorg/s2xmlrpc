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
package org.seasar.remoting.xmlrpc.converter;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import junit.framework.TestCase;

import org.seasar.remoting.xmlrpc.test.dto.EmployeeDto;
import org.seasar.remoting.xmlrpc.test.entity.Employee;

public class BeanTypeConverterTest extends TestCase {

    private Map empMap = new TreeMap();

    protected void setUp() throws Exception {
        Map dept1 = new HashMap();
        dept1.put("deptno", new Integer(1));
        dept1.put("dname", "dname1");

        Map dept2 = new HashMap();
        dept2.put("deptno", new Integer(2));
        dept2.put("dname", "dname2");

        Map role1 = new HashMap();
        role1.put("roleId",new Integer(1));
        role1.put("roleName", "admin");

        Map role2 = new HashMap();
        role2.put("roleId",new Integer(2));
        role2.put("roleName", "manager");

        Map role3 = new HashMap();
        role3.put("roleId",new Integer(3));
        role3.put("roleName", "user");

        {
            Map emp = new HashMap();
            emp.put("empno", new Integer(1));
            emp.put("ename", "ename1");
            emp.put("depertment", dept1);
            emp.put("roles", new Object[] { role1, role2, role3 });
            this.empMap.put(emp.get("empno"), emp);
        }
        {
            Map emp = new HashMap();
            emp.put("empno", new Integer(2));
            emp.put("ename", "ename2");
            emp.put("depertment", dept2);
            emp.put("roles", new Object[] { role3 });
            this.empMap.put(emp.get("empno"), emp);
        }
        {
            Map emp = new HashMap();
            emp.put("empno", new Integer(3));
            emp.put("ename", "ename3");
            emp.put("depertment", dept2);
            this.empMap.put(emp.get("empno"), emp);
        }
    }

	
	public void testConvert_Scalar() throws Exception {
		BeanTypeConverter converter = new BeanTypeConverter(Employee.class);
		Map src = new HashMap();
		src.put("firstName", "first1");
		src.put("lastName", "last1");
		src.put("age", new Integer(1));
		Employee emp = (Employee) converter.convert(new Object[]{src});
		assertEquals("first1", emp.getFirstName());
		assertEquals("last1", emp.getLastName());
		assertEquals(1, emp.getAge());
	}

	public void testConvert_Array() throws Exception {
		BeanTypeConverter converter = new BeanTypeConverter(Employee[].class);
		Map src = new HashMap();
		src.put("firstName", "first1");
		src.put("lastName", "last1");
		src.put("age", new Integer(1));
		Employee[] emps = (Employee[]) converter.convert(new Object[]{src});
		assertEquals(1, emps.length);
		Employee emp = emps[0];
		assertEquals("first1", emp.getFirstName());
		assertEquals("last1", emp.getLastName());
		assertEquals(1, emp.getAge());
	}

	public void testConvert_Nested() throws Exception {
		BeanTypeConverter converter = new BeanTypeConverter(EmployeeDto.class);
		Map src = (Map) empMap.get(new Integer(1));
		EmployeeDto emp = (EmployeeDto) converter.convert(new Object[]{src});
		assertEquals(new Integer(1), emp.getEmpno());
		assertEquals("ename1", emp.getEname());
		assertEquals(1, emp.getDepertment().getDeptno());
		assertEquals("dname1", emp.getDepertment().getDname());
		assertEquals(3, emp.getRoles().length);
		assertEquals(1, emp.getRoles()[0].getRoleId());
		assertEquals("admin", emp.getRoles()[0].getRoleName());
		assertEquals(2, emp.getRoles()[1].getRoleId());
		assertEquals("manager", emp.getRoles()[1].getRoleName());
		assertEquals(3, emp.getRoles()[2].getRoleId());
		assertEquals("user", emp.getRoles()[2].getRoleName());
	}
}
