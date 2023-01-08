package az.spring.jdbc;

import az.spring.jdbc.config.SpringJbcConfig;
import az.spring.jdbc.dao.EmployeeDao;
import az.spring.jdbc.model.Employee;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class JdbcApplication {
    public static void main(String[] args) {
//        ApplicationContext context=new ClassPathXmlApplicationContext("beans/spring-jdbc-config.xml");
//        EmployeeDao employeeDao= context.getBean(EmployeeDao.class);

        ApplicationContext context=new AnnotationConfigApplicationContext(SpringJbcConfig.class);
        EmployeeDao employeeDao= context.getBean(EmployeeDao.class);

        Employee emp=new Employee();
        emp.setName("Musa");
        emp.setSurname("Sariyev");
        emp.setAge(27);
        emp.setSalary(1000);
        employeeDao.insert(emp);

        Employee em=new Employee();
        em.setName("Murad");
        em.setSurname("Mirzeyev");
        em.setAge(34);
        em.setSalary(20000);
        em.setId(2);
        employeeDao.update(em);

        employeeDao.delete(8);
        long count=employeeDao.count();
        System.out.println("Say= "+count);

        System.out.println(employeeDao.getAllEmployees());

        Employee employee=employeeDao.getEmployeeById(4);
        System.out.println(employee);
    }
}
