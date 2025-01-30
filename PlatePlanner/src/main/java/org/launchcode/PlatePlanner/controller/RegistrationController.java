package org.launchcode.PlatePlanner.controller;

import io.micrometer.common.util.StringUtils;
import org.launchcode.PlatePlanner.exception.InvalidTokenException;
import org.launchcode.PlatePlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
@CrossOrigin(origins = "http://localhost:5173")
public class RegistrationController {

    private static final String REDIRECT_LOGIN = "redirect:/login";

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/verify")
    public String verifyUser(@RequestParam String token, RedirectAttributes redirectAttributes) {
        if (StringUtils.isEmpty(token)) {
            redirectAttributes.addFlashAttribute("tokenError", "Token is invlid");
            return REDIRECT_LOGIN;
        }
        try {
            userService.verifyUser(token);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("tokenError", "Token is invlid");
            return REDIRECT_LOGIN;
        }

        redirectAttributes.addFlashAttribute("message",
                messageSource.getMessage("verification.email.msg", null, LocaleContextHolder.getLocale()));
        return REDIRECT_LOGIN;
    }

}
