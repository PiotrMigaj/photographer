package pl.niebieskie_aparaty.photographer.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class HomeController {


    private List<String> photoUrls = new ArrayList<>();

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/portfolio")
    public String portfolio(Model model) {
        model.addAttribute("photoUrls", photoUrls);
        return "portfolio";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @PostMapping("/contact")
    public String handleContact(@RequestParam String name, @RequestParam String email, @RequestParam String message, Model model) {
        // Log the contact form input
        log.info("Contact form submitted by: Name = {}, Email = {}, Message = {}", name, email, message);

        // Add success message to the model
        model.addAttribute("successMessage", "Thank you for contacting us, " + name + "!");
        return "contact";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password. Please try again.");
        }
        return "login";
    }

    @GetMapping("/addPhoto")
    public String addPhoto() {
        return "addPhoto";
    }

    @PostMapping("/addPhoto")
    public String handleAddPhoto(@RequestParam String photoUrl, Model model) {
        log.info("New photo URL added: {}", photoUrl);
        photoUrls.add(photoUrl);
        return "redirect:/portfolio";
    }
}

