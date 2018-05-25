package project.ck1.thituluan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.ck1.thituluan.dto.FormGiaoVienDto;
import project.ck1.thituluan.dto.FormTraLoiDto;
import project.ck1.thituluan.entity.CauHoi;
import project.ck1.thituluan.entity.TaiKhoan;
import project.ck1.thituluan.entity.TraLoi;
import project.ck1.thituluan.repository.CauHoiRepository;
import project.ck1.thituluan.repository.TaiKhoanRepository;
import project.ck1.thituluan.repository.TraLoiRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
public class DefaultController {

    @Autowired
    TaiKhoanRepository taiKhoanRepository;
    @Autowired
    CauHoiRepository cauHoiRepository;
    @Autowired
    TraLoiRepository traLoiRepository;
    @GetMapping("/")
    public String home1() {
        return "/home";
    }

    @RequestMapping(value = "/sinhvien-post",method = RequestMethod.POST)
    String postTraLoi(@ModelAttribute FormTraLoiDto formTraLoiDto){
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        TraLoi traLoi = new TraLoi();
        traLoi.setSttCauHoi(formTraLoiDto.getSttCauHoi());
        traLoi.setCauTraLoi(formTraLoiDto.getCauTraLoi());
        traLoi.setUsername(username);
        traLoiRepository.save(traLoi);

        int question = formTraLoiDto.getSttCauHoi();
        if(formTraLoiDto.getHanhDong().equals("next")){
            question+=1;
        }
        else{
            question-=1;
        }
        String url= "redirect:/sinhvien?question=" + question;
        return url;
    }

    @GetMapping("/home")
    public String home() {
        return "/home";
    }

    @GetMapping("/giaovien")
    public String giaovien(Model model) {
        List<TaiKhoan> taiKhoans = taiKhoanRepository.findAll();
        List<String> Usernames = taiKhoans.stream().filter(taiKhoan -> taiKhoan.getCapbac().equals("SV")).map(taiKhoan -> taiKhoan.getUsername())
                .collect(Collectors.toList());

        model.addAttribute("showDetail",false);
        model.addAttribute("Usernames", Usernames);
        return "/giaovien";
    }

    @GetMapping("/traloi")
    public String traloi(Model model, @RequestParam("sv") String sv){
        List<TaiKhoan> taiKhoans = taiKhoanRepository.findAll();
        List<String> Usernames = taiKhoans.stream().filter(taiKhoan -> taiKhoan.getCapbac().equals("SV")).map(taiKhoan -> taiKhoan.getUsername())
                .collect(Collectors.toList());

        List<TraLoi> traLoiAll = traLoiRepository.findAll();
        List<TraLoi> traLoiSV= traLoiAll.stream().filter(tl -> tl.getUsername().equals(sv)).collect(Collectors.toList());

        List<CauHoi> cauHois = cauHoiRepository.findAll();

        List<FormGiaoVienDto> formGiaoVienDtos = new ArrayList<>();
        for(CauHoi cauHoi : cauHois){
            FormGiaoVienDto formGiaoVienDto = new FormGiaoVienDto();
            formGiaoVienDto.setSttCauHoi(cauHoi.getSttCauHoi());
            formGiaoVienDto.setCauHoi(cauHoi.getNoiDungCauHoi());
            formGiaoVienDto.setCauTraLoiChinhXac(cauHoi.getCauTraLoiChinhXac());
            for(TraLoi traLoi : traLoiSV){
                if(traLoi.getSttCauHoi() == cauHoi.getSttCauHoi()){
                    formGiaoVienDto.setCauTraLoiSV(traLoi.getCauTraLoi());
                    break;
                }
            }
            formGiaoVienDtos.add(formGiaoVienDto);
        }

        model.addAttribute("formGiaoVienDtos",formGiaoVienDtos);
        model.addAttribute("Usernames", Usernames);
        model.addAttribute("showDetail", true);
        model.addAttribute("sv",sv);
        return "/giaovien";
    }

    @GetMapping("/sinhvien")
    public String sinhvien(Model model,@RequestParam("question") int q) {
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        TraLoi traLoi = findTraLoiByUsernameCauHoi(username,q);
        traLoi.setSttCauHoi(q);
        CauHoi cauHoi = findCauHoiBySTT(q);

        FormTraLoiDto formTraLoiDto = new FormTraLoiDto()
                .builder()
                .sttCauHoi(q)
                .cauHoi(cauHoi.getNoiDungCauHoi())
                .cauTraLoi(traLoi.getCauTraLoi())
                .build();

        model.addAttribute("formTraLoiDto",formTraLoiDto);
        return "/sinhvien";
    }

    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/403")
    public String error403() {
        return "/error/403";
    }

    TraLoi findTraLoiByUsernameCauHoi(String username, int q){
       List<TraLoi> traLois = traLoiRepository.findAll();
       TraLoi traLoi = new TraLoi();
       for(TraLoi tl : traLois){
           if(tl.getUsername().equals(username)){
               if(tl.getSttCauHoi() == q) {
                   traLoi = tl;
               }
           }
       }
       return traLoi;
    }

    CauHoi findCauHoiBySTT(int stt){
        List<CauHoi> cauHois = cauHoiRepository.findAll();
        CauHoi cauHoi = new CauHoi();
        for(CauHoi ch : cauHois){
            if(ch.getSttCauHoi() == stt){
                cauHoi = ch;
            }
        }
        return cauHoi;
    }
}
