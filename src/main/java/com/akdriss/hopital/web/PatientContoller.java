package com.akdriss.hopital.web;


import com.akdriss.hopital.entities.Patient;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientContoller {

    private PatientRepository patientRepository;

    @GetMapping("index")
    public String index(Model model,
                        @RequestParam(name = "page" , defaultValue = "0") int  page,
                        @RequestParam(name = "size" ,defaultValue = "10")int size,
                        @RequestParam(name = "keyword" ,defaultValue = "")String kw
    ){
        Page<Patient> patients=patientRepository.findByNomContains(kw,PageRequest.of(page,size));
        model.addAttribute("patientList",patients.getContent());
        model.addAttribute("totalPages",new int[patients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",kw);

        return "patients";
    }
    @GetMapping("delete")
    public String delete(Model model,
                        @RequestParam(name = "page" , defaultValue = "0") int  page,
                        @RequestParam(name = "size" ,defaultValue = "10")int size,
                        @RequestParam(name = "keyword" ,defaultValue = "")String kw,
                         @RequestParam(name = "id" ,defaultValue = "")Long id

    ){
        patientRepository.deleteById(id);

        return "redirect:/index?page="+page+"&keyword="+kw;
    }
}
