package com.example.springfullproject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    CredentialRepository credentialRepository;



    @GetMapping("/")
    public String getLandingPage(){
        return "landing";
    }



    @PostMapping("/login")
    public String signin(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         HttpSession session, Model model){

        Optional<Credential> credential=credentialRepository.findById(username);

        if(credential.isPresent() && credential.get().getPassword().equals(password)){

            session.setAttribute("username", username);
            Optional<Userdetail> userdetail=userdetailRepository.findById(username);
            List<Usertypelink> usertypelinks=usertypelinkRepository.findAll();
            Optional<Usertypelink> usertypelink=usertypelinks.stream().filter(usertypelink1 -> usertypelink1.getUsername().equals(username)).findAny();
            if (userdetail.isPresent()){
                if (usertypelink.isPresent() && usertypelink.get().getType().equals("seller")){
                    model.addAttribute("userdetail",userdetail.get());
                    return "sellerdashboard";
                }else if (usertypelink.isPresent() && usertypelink.get().getType().equals("buyer")){
                    model.addAttribute("userdetail",userdetail.get());
                    return "buyerdashboard";
                }
            }
            else {
                return "information";
            }

        };

        return "/";
    }



    @PostMapping ("/signup")
    public String signup(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session){
        Credential credential=new Credential();
        credential.setUsername(username);
        credential.setPassword(password);
        session.setAttribute("username", username);

        credentialRepository.save(credential);
        return "information";
    }




    @Autowired
    UserdetailRepository userdetailRepository;

    @Autowired
    UsertypelinkRepository usertypelinkRepository;

    @PostMapping("/details")
    public String details(@RequestParam("fname") String fname,
                          @RequestParam("lname") String lname,@RequestParam("email") String email,
                          @RequestParam("phone") String phone, HttpSession session,
                          @RequestParam(name="buyer", defaultValue="false")boolean buyer,
                          @RequestParam(name = "seller", defaultValue = "false") boolean seller){

        Userdetail userdetail=new Userdetail();
        Usertypelink usertypelink=new Usertypelink();

        userdetail.setUsername((String) session.getAttribute("username"));
        userdetail.setFname(fname);
        userdetail.setLname(lname);
        userdetail.setEmail(email);
        userdetail.setPhone(phone);
        userdetailRepository.save(userdetail);

        Random random=new Random();
        int index= random.nextInt(1000);


        if (buyer){
            usertypelink.setId(String.valueOf(index));
            usertypelink.setUsername((String) session.getAttribute("username"));
            usertypelink.setType("buyer");
            usertypelinkRepository.save(usertypelink);

        }
        if (seller){
            usertypelink.setId(String.valueOf(index));
            usertypelink.setUsername((String) session.getAttribute("username"));
            usertypelink.setType("seller");
            usertypelinkRepository.save(usertypelink);
        }
        return "landing";
    }


}
