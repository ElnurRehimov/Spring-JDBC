package az.spring.jdbc.dao.impl;

import az.spring.jdbc.dao.EmployeeDao;
import az.spring.jdbc.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
    //    private final JdbcTemplate jdbcTemplate; //named etdikde ? qoymursan :name yazirsan buda bir yol
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public EmployeeDaoImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void insert(Employee employee) {
        String query = "insert into  employee(name,surname,age,salary) values(:name,:surname,:age,:salary)";

//        SqlParameterSource parameters = new MapSqlParameterSource() //bir bir get elemirik spring bizi qabaga salir ozu edir asagida
//                .addValue("name", employee.getName())
//                .addValue("surname", employee.getSurname())
//                .addValue("age", employee.getAge())
//                .addValue("salary", employee.getSalary());

        SqlParameterSource parameters =new BeanPropertySqlParameterSource(employee);
        jdbcTemplate.update(query,parameters);

    }

    @Override
    public void update(Employee employee) {
        String query = "update employee set name=:name,surname=:surname,age=:age,salary=:salary where id=:id";
        SqlParameterSource parameters =new BeanPropertySqlParameterSource(employee);
        jdbcTemplate.update(query,parameters);
    }

    @Override
    public void delete(int id) {
        String query = "delete from employee where id=:id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id",id);
        jdbcTemplate.update(query, parameters);
    }

    @Override
    public Employee getEmployeeById(int id) {
//        String query="select name from employee where id=?"; //name yox * olduqda String.class yerine Employee.class
//        // spring cevire bilmir RowMapper isletmeliyik
//       String name= jdbcTemplate.queryForObject(query,String.class,id);
//       Employee employee=new Employee();
//       employee.setName(name);
//       return  employee;
//        String query = "select * from employee where id=:id";
////        Employee employee= jdbcTemplate.queryForObject(query,new EmployeeRowMapper(),id);
//        Employee employee = jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<Employee>(Employee.class), id);
//        return employee;
        String query = "select * from employee where id=:id";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id",id);
        Employee employee = jdbcTemplate.queryForObject(query,parameters, new BeanPropertyRowMapper<Employee>(Employee.class));
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        String sql = "select * from employee";
        List<Employee> employees = jdbcTemplate.query(sql, new EmployeeRowMapper());
        return employees;
    }

    @Override
    public long count() {
        String query = "select count(*) from employee";
        long count = jdbcTemplate.queryForObject(query,new MapSqlParameterSource(), Long.class);
        return count;
    }

    public static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("surname"),
                    rs.getInt("age"),
                    rs.getDouble("salary")
            );
        }
    }
}
