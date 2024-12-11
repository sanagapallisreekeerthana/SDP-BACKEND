package com.example.demo.service;

import com.example.demo.entity.Assignment;
import com.example.demo.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AssignmentService {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private AssignmentRepository assignmentRepository;

    public void uploadAssignment(MultipartFile file, Long studentId) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filePath = UPLOAD_DIR + file.getOriginalFilename();
        file.transferTo(new File(filePath));

        Assignment assignment = new Assignment();
        assignment.setStudentId(studentId);
        assignment.setFileName(file.getOriginalFilename());
        assignment.setFilePath(filePath);
        assignment.setReviewed(false);
        assignmentRepository.save(assignment);
    }

    public void reviewAssignment(Long assignmentId, int marks) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
        if (!assignmentOptional.isPresent()) {
            throw new IllegalArgumentException("Assignment not found");
        }

        Assignment assignment = assignmentOptional.get();
        assignment.setMarks(marks);
        assignment.setReviewed(true);
        assignmentRepository.save(assignment);
    }
}