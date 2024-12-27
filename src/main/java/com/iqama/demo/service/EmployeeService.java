package com.iqama.demo.service;

import com.iqama.demo.Exception.ResourceNotFoundException;
import com.iqama.demo.entity.Employee;
import com.iqama.demo.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public Employee createEmployee(Employee employee) {
        System.out.println("Employee Created: " + employee);
      return   employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return   employeeRepository.findById(id);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {

        // Retrieve the existing employee
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));

        // Update only the fields that are not null in the incoming object
        if (updatedEmployee.getName() != null) {
            existingEmployee.setName(updatedEmployee.getName());
        }
        if (updatedEmployee.getEmail() != null) {
            existingEmployee.setEmail(updatedEmployee.getEmail());
        }
        if (updatedEmployee.getExpiryDate() != null) {
            existingEmployee.setExpiryDate(updatedEmployee.getExpiryDate());
        }
        if (updatedEmployee.getRole() != null) {
            existingEmployee.setRole(updatedEmployee.getRole());
        }

        return employeeRepository.save(existingEmployee);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }


    public List<Employee> getAllEmployee() throws ResourceNotFoundException {
        try {
            List<Employee> allService = employeeRepository.findAll();

            return allService;

        } catch (Exception e) {
            throw new ResourceNotFoundException("Could not Get all Service ");
        }

    }

//    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
//@Scheduled(cron = "0 * * * * ?") // Runs every minute
//public void checkAndSendExpiryNotifications() {
//        System.out.println("Check Expiry Notifications");
//        List<Employee> employees = employeeRepository.findAll();
//        LocalDate currentDate = LocalDate.now();
//
//        for (Employee employee : employees) {
//            if (employee.getExpiryDate() != null) {
//                long daysDifference = ChronoUnit.DAYS.between(currentDate, employee.getExpiryDate());
//                if (daysDifference <= 30) {
//                    sendExpiryNotification(employee);
//                }
//            }
//        }
//    }

//    private void sendExpiryNotification( Employee employee) {
//        System.out.println("Sending expiry notification");
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(employee.getEmail());
//        message.setSubject("Your Iqama Demo Account has Expired");
//        message.setText("Dear " + employee.getName() + ",\n\n" +
//                "Your Iqama Demo Account has expired. Please contact us at <EMAIL> to renew your account.\n\n" +
//                "Thank you,\n" +
//                "Iqama Demo Team");
//        javaMailSender.send(message);
//        System.out.println("Expiry notification sent");
//    }


//    @Scheduled(cron = "0 * * * * ?") // Runs every minute for testing; change as needed.
    public void checkAndSendExpiryNotifications() {
        System.out.println("Check Expiry Notifications");
        List<Employee> employees = employeeRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        // Collect employees with expired IDs or IDs expiring within 30 days
        StringBuilder expiringEmployeesList = new StringBuilder();
        for (Employee employee : employees) {
            if (employee.getExpiryDate() != null) {
                long daysDifference = ChronoUnit.DAYS.between(currentDate, employee.getExpiryDate());
                if (daysDifference <= 30) {
                    expiringEmployeesList.append(formatEmployeeDetails(employee, daysDifference));
                }
            }
        }

        // If there are employees to notify about, send the email
        if (expiringEmployeesList.length() > 0) {
            sendExpiryNotification(expiringEmployeesList.toString());
        }
    }

    private void sendExpiryNotification(String employeeDetails) {
        System.out.println("Sending expiry notification");

        String notificationRecipient = "jithin.ramachandran@resemblesystems.com";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notificationRecipient);
        message.setSubject("Expiry Notifications for Employees");
        message.setText("The following employees have IDs that are expired or will expire within 30 days:\n\n"
                + employeeDetails + "\n\n"
                + "Thank you,\n"
                + "Iqama Demo Team");
        javaMailSender.send(message);

        System.out.println("Expiry notification sent to " + notificationRecipient);
    }

    private String formatEmployeeDetails(Employee employee, long daysDifference) {
        String status = daysDifference < 0 ? "Expired" : "Expiring in " + daysDifference + " days";
        return String.format("Employee Name: %s\nEmail: %s\nExpiry Date: %s\nStatus: %s\n\n",
                employee.getName(), employee.getEmail(), employee.getExpiryDate(), status);
    }




    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }



    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

//    public void sendExpiryNotification(Employee employee) {
//        String toEmail = "arjun@resemblesystems.com"; // Replace with the recipient's email address
//        String subject = "Expiry Notification for Employee: " + employee.getName();
//        String body = String.format("Employee Name: %s\nEmail: %s\nExpiry Date: %s",
//                employee.getName(),
//                employee.getEmail(),
//                employee.getExpiryDate());
//
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setTo(toEmail);
//            helper.setSubject(subject);
//            helper.setText(body);
//            javaMailSender.send(message);
//            System.out.println("Email sent successfully for employee: " + employee.getName());
//        } catch (MessagingException e) {
//            System.err.println("Failed to send email for employee: " + employee.getName());
//            e.printStackTrace();
//        }
//    }

}
