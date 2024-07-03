package it.uniroma3.siw.controller;

import java.io.IOException;

import org.hibernate.annotations.processing.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Cuoco;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.CuocoService;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;

@Controller
public class AuthenticationController {
	
	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private UserService userService;
    
    @Autowired
	private CuocoService cuocoService;
	
    @Autowired
    private UserValidator userValidator;
    
    @Autowired 
    private CredentialsValidator  credentialsValidator;
	
    @GetMapping(value = "/login") 
	public String showLoginForm (Model model) {
		return "formLogin";
	}

	@GetMapping(value = "/register") 
	public String showRegisterForm (Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("credentials", new Credentials());
		model.addAttribute("cuoco", new Cuoco());

		return "formRegisterUser";
	}
	
	
	@GetMapping(value = "/") 
	public String index(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}
		else {		
			UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
			if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
				return "admin/indexAdmin.html";
			}
			if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
				Cuoco cuoco=this.cuocoService.findByNomeAndCognome(credentials.getUser().getName(),credentials.getUser().getSurname());
	    		model.addAttribute("cuoco", cuoco);
				return "cuoco/indexCuoco.html";
	        }
		}
        return "index.html";
	}
		
    @GetMapping(value = "/success")
    public String defaultAfterLogin(Model model) {
        
    	UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
    	if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
            return "admin/indexAdmin.html";
        }
    	if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
    		Cuoco cuoco=this.cuocoService.findByNomeAndCognome(credentials.getUser().getName(),credentials.getUser().getSurname());
    		model.addAttribute("cuoco", cuoco);
            return "cuoco/indexCuoco.html";
        }
        return "index.html";
    }

	@PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult userBindingResult,
    		@Valid @ModelAttribute("credentials") Credentials credentials,BindingResult credentialsBindingResult, 
            @Valid @ModelAttribute("cuoco") Cuoco cuoco, BindingResult cuocoBindingResult,
                 @RequestParam("imageFile") MultipartFile imageFile,
                 Model model) throws IOException {
		

		// se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        	cuoco.setNome(user.getName());
        	cuoco.setCognome(user.getSurname());
        	user.setCuoco(cuoco);
        	cuocoService.save(cuoco,imageFile);
        	userService.saveUser(user);
            credentials.setUser(user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "registrationSuccessful";
      
    }
}