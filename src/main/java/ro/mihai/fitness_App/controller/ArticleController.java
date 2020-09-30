package ro.mihai.fitness_App.controller;


import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ro.mihai.fitness_App.database.*;
import ro.mihai.fitness_App.security.UserSession;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
@SessionScope
public class ArticleController {

    @Autowired
    UserService userService;
    @Autowired
    UserSession userSession;
    @Autowired
    ArticleDao articleDao;
    @Autowired
    ArticleArchiveDao articleArchiveDao;
    @Autowired
    UserDao userDao;


    @GetMapping("dashboard")

    public ModelAndView dashboard(@RequestParam("email") String email,
                                  @RequestParam("code") String pass,
                                  @RequestParam("categ_id") int categ_id,
                                  @ModelAttribute("message") String message,
                                  @ModelAttribute("message1") String message1) {
        //accesare url doar daca utilizatorul s-a logat
        if (userSession.getUserId() == 0) {
            return new ModelAndView("home-page");
        }
        User user = userDao.findByEmail(email);
        //securitate url. email-ul trebuie sa se potriveasca cu parola criptata
        if (user.getPassword().equals(pass)) {
            ModelAndView modelAndView = new ModelAndView("dashboard");
            modelAndView.addObject("message", message);
            modelAndView.addObject("message1", message1);
            List<Article> articlesN = articleDao.findAllByCategoryId(1);
            List<Article> articleW = articleDao.findAllByCategoryIdAndChosenOption(2, user.getChosenOption());
            List<Article> articleNu = articleDao.findAllByCategoryIdAndChosenOption(3, user.getChosenOption());
            if (categ_id == 1) {
                modelAndView.addObject("article", articlesN);
                modelAndView.addObject("user", user);
            }
            if (categ_id == 2) {
                modelAndView.addObject("article", articleW);
                modelAndView.addObject("user", user);
            }
            if (categ_id == 3) {
                modelAndView.addObject("article", articleNu);
                modelAndView.addObject("user", user);
            }
            modelAndView.addObject("articleN", articlesN);
            modelAndView.addObject("articleW", articleW);
            modelAndView.addObject("articleNu", articleNu);
            modelAndView.addObject("user", user);
            return modelAndView;
        } else return new ModelAndView("home-page");
    }

    @GetMapping("/article")
    public ModelAndView article(@RequestParam(value = "id") Integer id,
                                @RequestParam("email") String email,
                                @RequestParam("code") String pass,
                                @RequestParam("option") int option) {

        //accesare url doar daca utilizatorul s-a logat
        if (userSession.getUserId() == 0) {
            return new ModelAndView("home-page");
        }
        User user = userDao.findByEmail(email);
        //securitate url. email-ul trebuie sa se potriveasca cu parola criptata
        if (user.getPassword().equals(pass)) {
            ModelAndView modelAndView = new ModelAndView("article-details-page");
            if (option == 1) {
                Article article = articleDao.findById(id);
                modelAndView.addObject("user", user);
                modelAndView.addObject("article", article);
            }
            if (option == 2) {
                ArticleArchive article = articleArchiveDao.findById(id);
                modelAndView.addObject("user", user);
                modelAndView.addObject("article", article);
            }

            return modelAndView;

        } else
            return new ModelAndView("home-page");
    }

    //salvare din pagina principala
    @PostMapping("save-article")
    public ModelAndView savearticle(@RequestParam("title") String title,
                                    @RequestParam("description") String description,
                                    @RequestParam("content") String content,
                                    @RequestParam("photoFile") String photoFile,
                                    @RequestParam("category_id") int category_id,
                                    @RequestParam("user_id") int user_id,
                                    @RequestParam("email") String email,
                                    @RequestParam("code") String pass,
                                    RedirectAttributes redirectAttributes) {
        List<ArticleArchive> articleArchives = articleArchiveDao.findAllByTitle(title);

        //daca articolul se afla deja in arhiva acesta nu se salveaza a doua oara
        if (articleArchives.size() >= 1) {
            redirectAttributes.addAttribute("email", email);
            redirectAttributes.addAttribute("code", pass);
            redirectAttributes.addAttribute("categ_id", category_id);
            redirectAttributes.addFlashAttribute("message", "Articol este salvat deja in arhiva");
            return new ModelAndView("redirect:/dashboard");
        } else
            articleArchiveDao.save(title, description, content, photoFile, user_id, category_id);
        redirectAttributes.addAttribute("email", email);
        redirectAttributes.addAttribute("code", pass);
        redirectAttributes.addAttribute("categ_id", category_id);
        redirectAttributes.addFlashAttribute("message1", "Articolul a fost salvat cu success");
        return new ModelAndView("redirect:/dashboard");
    }

    //salvare din pagina article-details
    @PostMapping(" save-article")
    public ModelAndView savearticle1(@RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     @RequestParam("content") String content,
                                     @RequestParam("photoFile") String photoFile,
                                     @RequestParam("id") int id,
                                     @RequestParam("user_id") int user_id,
                                     @RequestParam("category_id") int category_id,
                                     @RequestParam("email") String email,
                                     @RequestParam("code") String pass,
                                     RedirectAttributes redirectAttributes) {
        List<ArticleArchive> articleArchives = articleArchiveDao.findAllByTitle(title);

        //daca articolul se afla deja in arhiva acesta nu se salveaza a doua oara
        if (articleArchives.size() >= 1) {
            ModelAndView modelAndView = new ModelAndView("article-details-page");
            Article article = articleDao.findById(id);
            User user = userDao.findByEmail(email);
            modelAndView.addObject("user", user);
            modelAndView.addObject("article", article);
            modelAndView.addObject("message", "Articolul este salvat deja in arhiva!");
            redirectAttributes.addAttribute("email", email);
            redirectAttributes.addAttribute("code", pass);
            redirectAttributes.addAttribute("categ_id", category_id);
            return modelAndView;
        } else
            articleArchiveDao.save(title, description, content, photoFile, user_id, category_id);
        ModelAndView modelAndView = new ModelAndView("article-details-page");
        Article article = articleDao.findById(id);
        User user = userDao.findByEmail(email);
        modelAndView.addObject("user", user);
        modelAndView.addObject("article", article);
        modelAndView.addObject("message1", "Articolul a fost salvat cu success!");
        redirectAttributes.addAttribute("email", email);
        redirectAttributes.addAttribute("code", pass);
        redirectAttributes.addAttribute("categ_id", category_id);
        return modelAndView;
    }

    @GetMapping("personal-archive")
    public ModelAndView archive(@RequestParam("user_id") int user_id,
                                @RequestParam("categ_id") int categ_id,
                                @RequestParam("email") String email) {
        if (userSession.getUserId() == 0) {
            return new ModelAndView("home-page");
        }
        ModelAndView modelAndView = new ModelAndView("personal-archive-page");
        User user = userDao.findByEmail(email);
        List<ArticleArchive> articleArchivesN = articleArchiveDao.findAllByUserIdAndCategoryId(user_id, 1);
        List<ArticleArchive> articleArchivesW = articleArchiveDao.findAllByUserIdAndCategoryId(user_id, 2);
        List<ArticleArchive> articleArchivesNu = articleArchiveDao.findAllByUserIdAndCategoryId(user_id, 3);
        if (categ_id == 1) {
            modelAndView.addObject("article", articleArchivesN);
            modelAndView.addObject("user", user);
        }
        if (categ_id == 2) {
            modelAndView.addObject("article", articleArchivesW);
            modelAndView.addObject("user", user);
        }
        if (categ_id == 3) {
            modelAndView.addObject("article", articleArchivesNu);
            modelAndView.addObject("user", user);
        }
        modelAndView.addObject("articleN", articleArchivesN);
        modelAndView.addObject("articleW", articleArchivesW);
        modelAndView.addObject("articleNu", articleArchivesNu);
        return modelAndView;
    }

}

