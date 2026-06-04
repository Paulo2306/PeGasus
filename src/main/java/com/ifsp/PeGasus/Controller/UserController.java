package com.ifsp.PeGasus.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.PeGasus.Model.User;
import com.ifsp.PeGasus.Enum.Tipo;
import com.ifsp.PeGasus.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/registrar")
    public String registrarForm(Model model) {
        model.addAttribute("user", new User());
        return "user/registrar";
    }

    @PostMapping("/user/registrar")
    public String registrar(User user, @RequestParam(value = "tipoUsuario", defaultValue = "CLIENTE") String tipoUsuario) {
        user.setTipo(Tipo.valueOf(tipoUsuario));
        userRepository.save(user);
        return "redirect:/user/logar";
    }

    @GetMapping("/user/logar")
    public String logarForm() {
        return "user/logar";
    }

    @PostMapping("/user/logar")
    public String logar(@RequestParam String nome, @RequestParam String senha, HttpSession session, Model model) {
        Optional<User> userOpt = userRepository.findByNomeAndSenha(nome, senha);
        if (userOpt.isPresent()) {
            session.setAttribute("usuarioLogado", userOpt.get());
            return "redirect:/produto/lista";
        }
        model.addAttribute("erro", "Usuário ou senha inválidos.");
        return "user/logar";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("usuarioLogado");
        session.invalidate();
        return "redirect:/user/logar";
    }
}
