package springproject.dev.ems_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springproject.dev.ems_backend.dto.EmployeeDto;
import springproject.dev.ems_backend.entity.Employee;
import springproject.dev.ems_backend.exception.ResourceNotFoundException;
import springproject.dev.ems_backend.mapper.EmployeeMapper;
import springproject.dev.ems_backend.repository.EmployeeRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        // Convert DTO to Entity
        Employee employee = EmployeeMapper.maptoEmployee(employeeDto);
        // Save entity to database
        Employee createEmployee = employeeRepository.save(employee);
        // Convert saved entity back to DTO
        return EmployeeMapper.mapToEmployeeDto(createEmployee);
    }

    @Override
    public EmployeeDto getEmployeeById(Long employeeId) {
      Employee employee = employeeRepository.findById(employeeId)
              .orElseThrow(() -> new ResourceNotFoundException("EmployeeId " +
                      "is not present with Given ID "+employeeId));
        return EmployeeMapper.mapToEmployeeDto(employee);
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
      List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map((employee)-> EmployeeMapper.mapToEmployeeDto(employee)).collect
                (Collectors.toList());
    }

    @Override
    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto updateEmployee) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeId is not present with Given ID "+employeeId));
        employee.setFirstName(updateEmployee.getFirstName());
        employee.setLastName(updateEmployee .getLastName());
        employee.setEmail(updateEmployee.getEmail());

        Employee updatedemployee = employeeRepository.save(employee);
        return EmployeeMapper.mapToEmployeeDto(updatedemployee);
    }

    @Override
    public void deleteEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("EmployeeId is not present with Given ID "+employeeId));

        employeeRepository.deleteById(employeeId);

    }

    @Override
    public void deleteAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        employeeRepository.deleteAll();
    }


}
