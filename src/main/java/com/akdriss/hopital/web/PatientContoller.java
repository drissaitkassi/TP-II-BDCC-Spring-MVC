package com.akdriss.hopital.web;


import com.akdriss.hopital.entities.Patient;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("formPatient")
    public String addPatient(Model model

    ){

        model.addAttribute("patient",new Patient());
        return "formPatient";
    }

    @GetMapping("editPatient")
    public String editPatient(Model model,
                              @RequestParam(name = "id") Long id,
                              @RequestParam(name = "page" , defaultValue = "0") int  page,
                              @RequestParam(name = "size" ,defaultValue = "10")int size,
                              @RequestParam(name = "keyword" ,defaultValue = "")String kw

    ){

       Patient patient= patientRepository.findById(id).orElse(null);
       if (patient == null)throw new RuntimeException("patient introuvable");
       model.addAttribute("patient",patient);
        //todo add attributes for /save
        model.addAttribute("page",page);
        model.addAttribute("keyword",kw);
        return "editPatient";
    }

    @PostMapping("edit")
    public String updatePatient(Model model, @Valid Patient patient, BindingResult bindingResult
    ){
        if (bindingResult.hasErrors())return "editPatient";
        patientRepository.save(patient);

        return "redirect:editPatient";
    }

    @PostMapping("save")
    public String savePatient(Model model, @Valid Patient patient, BindingResult bindingResult,

                              @RequestParam(name = "page" , defaultValue = "0") int  page,
                              @RequestParam(name = "size" ,defaultValue = "10")int size,
                              @RequestParam(name = "keyword" ,defaultValue = "")String kw
                              ){
        if (bindingResult.hasErrors())return "formPatient";
        patientRepository.save(patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",kw);

        return "redirect:/index?page="+page+"&keyword="+kw;
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
