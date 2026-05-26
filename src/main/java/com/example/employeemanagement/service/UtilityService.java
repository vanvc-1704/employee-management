package com.example.employeemanagement.service;

import com.example.employeemanagement.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class UtilityService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public UtilityService(ModelMapper modelMapper, PasswordEncoder passwordEncoder, EmployeeRepository employeeRepository) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Format tên nhân viên: trim khoảng trắng và viết hoa chữ cái đầu mỗi từ.
     * Ví dụ: "  vu cong   van  " → "Vu Cong Van"
     */
    public String formatEmployeeName(String name) {
        if (name == null || name.isBlank()) return "";
        String[] words = name.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase())
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Sinh mã nhân viên tự động theo định dạng: EMP-YYYYMMDD-XXXX
     * Tìm max sequence của hôm nay từ DB để đảm bảo không trùng lặp
     * Ví dụ: EMP-20260526-0001
     */
    public String generateEmployeeCode() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String codePrefix = "EMP-" + datePart + "-";
        
        // Tìm tất cả code của hôm nay
        var employees = employeeRepository.findAll();
        int maxSeq = 0;
        
        for (var emp : employees) {
            if (emp.getCode() != null && emp.getCode().startsWith(codePrefix)) {
                try {
                    String seqPart = emp.getCode().substring(codePrefix.length());
                    int seq = Integer.parseInt(seqPart);
                    maxSeq = Math.max(maxSeq, seq);
                } catch (NumberFormatException e) {
                    // Skip invalid format
                }
            }
        }
        
        return String.format("%s%04d", codePrefix, maxSeq + 1);
    }

    /**
     * Mã hóa mật khẩu bằng BCrypt (dùng PasswordEncoder bean từ AppConfig).
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * Kiểm tra mật khẩu có khớp với hash không.
     */
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Map object từ source class sang target class (dùng ModelMapper bean từ AppConfig).
     */
    public <D> D map(Object source, Class<D> destinationType) {
        return modelMapper.map(source, destinationType);
    }
}
