package com.solu.printcenter.controller;

import com.solu.printcenter.repository.PrintRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


// load entire SpringBoot Application Context (end-to-end)
@SpringBootTest
// inject MockMvc instance
@AutoConfigureMockMvc
public class PrintRequestControllerIntegrationTest {

    // for HTTP requests
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PrintRequestRepository printRequestRepository;

    @BeforeEach
    void setUp() {
        // Clean up the database before each test
        printRequestRepository.deleteAll();
    }

    // JUnit test method
    @Test
    void testSchedulePrint_WithValidFilesShouldReturnOk() throws Exception {
        // create Mock file
        MockMultipartFile file = new MockMultipartFile(
                "files", "test.txt", MediaType.TEXT_PLAIN_VALUE, "test content".getBytes()
        );

        mockMvc.perform(multipart("/schedulePrint")
                        .file(file)
                        .param("color", "true")
                        .param("requestedBy", "user1")
                        .param("meetingOwner", "owner1")
                        .param("meetingDate", "2024-10-10")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Print Scheduled"));
    }

    @Test
    void testSchedulePrint_WithMultipleFilesShouldReturnOk() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "files", "test1.txt", MediaType.TEXT_PLAIN_VALUE, "test content".getBytes()
        );
        MockMultipartFile file2 = new MockMultipartFile(
                "files", "test2.txt", MediaType.TEXT_PLAIN_VALUE, "test content".getBytes()
        );

        mockMvc.perform(multipart("/schedulePrint")
                        .file(file1)
                        .file(file2)
                        .param("color", "true")
                        .param("requestedBy", "user1")
                        .param("meetingOwner", "owner1")
                        .param("meetingDate", "2024-10-10")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(content().string("Print Scheduled"));
    }

    @Test
    void testSchedulePrint_With2PeriodsFileShouldReturnBad() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "files", "test1..txt", MediaType.TEXT_PLAIN_VALUE, "test content".getBytes()
        );

        mockMvc.perform(multipart("/schedulePrint")
                        .file(file1)
                        .param("color", "true")
                        .param("requestedBy", "user1")
                        .param("meetingOwner", "owner1")
                        .param("meetingDate", "2024-10-10")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid file name: test1..txt"));
    }

    @Test
    void testSchedulePrint_WithNoPeriodsFileShouldReturnBad() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile(
                "files", "test1txt", MediaType.TEXT_PLAIN_VALUE, "test content".getBytes()
        );

        mockMvc.perform(multipart("/schedulePrint")
                        .file(file1)
                        .param("color", "true")
                        .param("requestedBy", "user1")
                        .param("meetingOwner", "owner1")
                        .param("meetingDate", "2024-10-10")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid file name: test1txt"));
    }

    @Test
    void testGetPrintRequests_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/getPrintRequests"))
                .andExpect(status().isOk());
    }

}