package com.solu.printcenter.service;

import com.solu.printcenter.domain.PrintRequest;
import com.solu.printcenter.repository.PrintRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PrintRequestService {

    PrintRequestRepository printRequestRepository;

    @Autowired
    public PrintRequestService(PrintRequestRepository printRequestRepository) {
        this.printRequestRepository = printRequestRepository;
    }

    public void schedulePrint(PrintRequest printRequest) {
        // Call Print API to schedule print and send over file(s) to print
    }

    public void saveFileInS3(MultipartFile file) {
        // Save file(s) in S3
    }

    public void savePrintRequest(PrintRequest printRequest) {
        printRequestRepository.save(printRequest);
    }

    public List<PrintRequest> getPrintRequests() {
        return printRequestRepository.findAll();
    }
}
