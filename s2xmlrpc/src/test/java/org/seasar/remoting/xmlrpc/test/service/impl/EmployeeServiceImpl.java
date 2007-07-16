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
package org.seasar.remoting.xmlrpc.test.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.remoting.xmlrpc.test.entity.Employee;
import org.seasar.remoting.xmlrpc.test.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

	public Employee[] getEmployees() {
		List employees = new ArrayList();
		employees.add(makeEmployee("first1", "last1", 1));
		return (Employee[]) employees.toArray(new Employee[0]);
	}

	private Employee makeEmployee(String firstName, String lastName, int age) {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setAge(age);
		return employee;
	}

	public List getEmployeeList() {
		return Arrays.asList(getEmployees());
	}

	public Map getEmployeeMap() {
		Map employee = new HashMap();
		employee.put("firstName", "first1");
		employee.put("lastName", "last1");
		employee.put("age", new Integer(1));
		return employee;
	}
	
	public Employee getEmployee() {
		return makeEmployee("first1", "last1", 1);
	}

	protected Employee protectecdGetEmployee() {
		return makeEmployee("first1", "last1", 1);
	}

	Employee packageGetEmployee() {
		return makeEmployee("first1", "last1", 1);
	}

	public Employee publicGetEmployee() {
		return makeEmployee("first1", "last1", 1);
	}
	
	public void voidMethod() {}

    public boolean setArrayStringParam(String[] arrayStringParam) {
    	return true;
    }
}
