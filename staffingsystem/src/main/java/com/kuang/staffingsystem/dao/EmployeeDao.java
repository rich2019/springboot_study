package com.kuang.staffingsystem.dao;

import com.kuang.staffingsystem.pojo.Department;
import com.kuang.staffingsystem.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Repository
public class EmployeeDao {

    @Autowired
    private DepartmentDao departmentDao;

    //模拟数据库中的数据
    private static Map<Integer, Employee> employees = null;

    static {
        employees = new HashMap<>();
        employees.put(101, new Employee(101, "aa", "112345@qq.com", 0, new Department(101, "教学部"), new Date()));
        employees.put(102, new Employee(102, "bb", "212345@qq.com", 1, new Department(102, "市场部"), new Date()));
        employees.put(103, new Employee(103, "cc", "312345@qq.com", 0, new Department(103, "教研部"), new Date()));
        employees.put(104, new Employee(104, "dd", "412345@qq.com", 1, new Department(104, "运营部"), new Date()));
        employees.put(105, new Employee(105, "ee", "512345@qq.com", 0, new Department(105, "后勤部"), new Date()));
    }

    private static int initId = 106;

    public void save(Employee employee) {

        if (employee.getId() == null) {
            employee.setId(initId++);
        }

        employee.setDepartment(departmentDao.getDepartmentById(employee.getDepartment().getId()));
        employees.put(employee.getId(), employee);
    }

    public Collection<Employee> getEmployees() {
        return employees.values();
    }

    public Employee getEmployeeById(Integer id) {
        return employees.get(id);
    }

    public void deleteById(Integer id) {
        employees.remove(id);
    }

}
