package ro.mihai.fitness_App.controller;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.mihai.fitness_App.database.*;
import ro.mihai.fitness_App.security.UserSession;
import ro.mihai.fitness_App.pop.up.msg.InvalidEmail;
import ro.mihai.fitness_App.pop.up.msg.InvalidPassword;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserDao userDao;
    @Autowired
    UserService userService;
    @Autowired
    UserSession userSession;
    @Autowired
    ArticleDao articleDao;


    @GetMapping("/")
    public ModelAndView homepage() {
        return new ModelAndView("home-page");
    }

    @GetMapping("/register")
    public ModelAndView registerpage() {
        return new ModelAndView("registration-page");
    }

    @PostMapping("/register-action")
    public ModelAndView registerAction(@RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       @RequestParam("name") String name,
                                       @RequestParam("chosenOption") Integer chosenOption) {
        ModelAndView modelAndView = new ModelAndView("registration-page");
        try {
            userService.save(email, password, name, chosenOption);
            modelAndView.addObject("message", "Contul a fost creat cu succes!  Revino la pagina principala apasand butonul Go Back ");
            return modelAndView;

        } catch (InvalidPassword invalidPassword) {
            String messageException = invalidPassword.getMessage();
            modelAndView.addObject("message1", messageException);
            return modelAndView;
        } catch (InvalidEmail invalidEmail) {
            String messageException = invalidEmail.getMessage();
            modelAndView.addObject("message2", messageException);
            return modelAndView;
        }
    }

    @PostMapping("/login-action")

    public ModelAndView loginAction(@RequestParam("email") String email,
                                    @RequestParam("password") String password,
                                    @RequestParam("categ_id") int categ_id,
                                    RedirectAttributes redirectAttributes
    ) {
        ModelAndView modelAndView = new ModelAndView("home-page");
        ModelAndView modelAndView1 = new ModelAndView("redirect:/dashboard");

        redirectAttributes.addAttribute("email", email);
        //se transmite parola criptata catre dashboard pentru securitate url.
        redirectAttributes.addAttribute("code", DigestUtils.md5Hex(password));
        redirectAttributes.addAttribute("categ_id", categ_id);

        User userByEmail = userDao.findByEmail(email);
        if (userDao.findByEmail(email) == null) {
            modelAndView.addObject("message3", "Credentialele nu sunt corecte");
            return modelAndView;
        }
        if (userByEmail.getPassword().equals(DigestUtils.md5Hex(password))) {

            userSession.setUserId(userByEmail.getId());

            return modelAndView1;
        } else {
            modelAndView.addObject("message3", "Credentialele nu sunt corecte");
            return modelAndView;
        }
    }

    @PostMapping("/logout")
    public ModelAndView homepageReturn() {
        userSession.setUserId(0);
        return new ModelAndView("home-page");
    }


}

