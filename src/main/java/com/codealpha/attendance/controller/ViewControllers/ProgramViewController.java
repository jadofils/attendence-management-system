package com.codealpha.attendance.controller.ViewControllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.core.ParameterizedTypeReference;

import com.codealpha.attendance.dto.ProgramDTO;

import java.util.List;

@Controller
@RequestMapping("/programs")
public class ProgramViewController {

    private final RestTemplate restTemplate;

    // Constructor injection
    public ProgramViewController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String getAllPrograms(Model model) {
        // Add an empty ProgramDTO for form binding
        model.addAttribute("programDTO", new ProgramDTO());
    
      
        // Fetch programs from API
        String apiUrl = "http://localhost:8080/api/programs";
        ResponseEntity<List<ProgramDTO>> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProgramDTO>>() {}
        );
        List<ProgramDTO> programs = response.getBody();
    
        // Add programs to model
        model.addAttribute("programs", programs);
    
        // Render the programs/select.html template
        return "programs/select";
    }
    
}
