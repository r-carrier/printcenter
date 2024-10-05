package com.solu.printcenter.controller;


import com.solu.printcenter.domain.PrintRequest;
import com.solu.printcenter.service.PrintRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
public class PrintRequestController {

    private final PrintRequestService printRequestService;

    public PrintRequestController(PrintRequestService printRequestService) {
        this.printRequestService = printRequestService;
    }

    @PostMapping("/schedulePrint")
    public ResponseEntity<String> schedulePrint(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("color") boolean color,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam("requestedBy") String requestedBy,
            @RequestParam("meetingOwner") String meetingOwner,
            @RequestParam("meetingDate") String meetingDate
    ) {

        LocalDate currentDate = LocalDate.now();

        if (files.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload!");
        } else if (files.size() > 10) {
            return ResponseEntity.badRequest().body("You can only print up to 10 files at a time!");
        }

        for (MultipartFile file : files) {
            // File name validation should be set in the UI as well so the request never makes it to the API
            // File size validation defined in application.properties file
            // Could also validate file type here
            String fileName = file.getOriginalFilename();
            if (fileName == null || fileName.contains("..")) {
                return ResponseEntity.badRequest().body("Invalid file name: {}");
            }

            // Create a new instance of PrintRequest for each file
            PrintRequest printRequest = new PrintRequest();
            printRequest.setFileName(fileName);
            printRequest.setColor(color);
            printRequest.setNotes(notes);
            printRequest.setRequestedBy(requestedBy);
            printRequest.setRequestDate(currentDate);
            printRequest.setMeetingOwner(meetingOwner);
            printRequest.setMeetingDate(LocalDate.parse(meetingDate));

            // What do we need to do if scheduling the print fails?
            try {
                printRequestService.schedulePrint(printRequest);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Failed to schedule print: {}");
            }
            // If we need to keep the file, save in S3
            // In a real-world scenario, we could return the ARN into a string and save it in the database
            printRequestService.saveFileInS3(file);
            // Save the print request to the database
            printRequestService.savePrintRequest(printRequest);

        }

        return ResponseEntity.ok("Print Scheduled");
    }

    @GetMapping("/getPrintRequests")
    public ResponseEntity<List<PrintRequest>> getPrintRequests() {
        List<PrintRequest> printRequests = printRequestService.getPrintRequests();
        return ResponseEntity.ok(printRequests);
    }

}
