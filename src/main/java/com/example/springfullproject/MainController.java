package com.example.springfullproject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
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
    public String signin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session){

        Optional<Credential> credential=credentialRepository.findById(username);

        if(credential.isPresent() && credential.get().getPassword().equals(password)){

            session.setAttribute("username", username);
            return "dashboard";
        };

        return "/";
    }



    @PostMapping ("/signup")
    public String signup(@RequestParam("username") String username, @RequestParam("password") String password){
        Credential credential=new Credential();
        credential.setUsername(username);
        credential.setPassword(password);

        credentialRepository.save(credential);
        return "dashboard";
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
        usertypelink.setId(String.valueOf(index));


        if (buyer){
            usertypelink.setUsername((String) session.getAttribute("username"));
            usertypelink.setType("buyer");
        }
        if (seller){
            usertypelink.setUsername((String) session.getAttribute("username"));
            usertypelink.setType("seller");
        }
        usertypelinkRepository.save(usertypelink);

        return "added";


    }

}
