package com.example.employeemanagement.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UtilityService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    // Sequence counter để tạo mã nhân viên duy nhất
    private static final AtomicInteger sequence = new AtomicInteger(1);

    public UtilityService(ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
     * Ví dụ: EMP-20260526-0001
     */
    public String generateEmployeeCode() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        int seq = sequence.getAndIncrement();
        return String.format("EMP-%s-%04d", datePart, seq);
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
