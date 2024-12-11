package com.example.demo.controller;

import com.example.demo.entity.Assignment;
import com.example.demo.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/api/assignments")
@CrossOrigin(origins = "http://localhost:8080")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @PostMapping("/upload")
    public Map<String, Object> uploadAssignment(@RequestParam("file") MultipartFile file, 
                                                 @RequestParam("studentId") Long studentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            assignmentService.uploadAssignment(file, studentId);
            response.put("success", true);
            response.put("message", "Assignment uploaded successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to upload assignment: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/review")
    public Map<String, Object> reviewAssignment(@RequestParam("assignmentId") Long assignmentId, 
                                                @RequestParam("marks") int marks) {
        Map<String, Object> response = new HashMap<>();
        try {
            assignmentService.reviewAssignment(assignmentId, marks);
            response.put("success", true);
            response.put("message", "Marks assigned successfully");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to assign marks: " + e.getMessage());
        }
        return response;
    }
}