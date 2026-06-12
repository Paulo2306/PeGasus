package com.ifsp.PeGasus.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.PeGasus.Enum.Tipo;
import com.ifsp.PeGasus.Model.User;
import com.ifsp.PeGasus.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/registrar")
    public String registrarForm() {
        return "user/registrar";
    }

    @PostMapping("/user/registrar")
    public String registrar(
            User user,
            @RequestParam(value = "tipoUsuario", defaultValue = "CLIENTE") String tipoUsuario,
            RedirectAttributes redirectAttributes) {

        // Verifica se já existe usuário com o mesmo nome
        if (userRepository.findByNome(user.getNome()).isPresent()) {
            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Já existe um usuário com esse nome.");

            return "redirect:/user/registrar";
        }

        user.setTipo(Tipo.valueOf(tipoUsuario));

        userRepository.save(user);

        redirectAttributes.addFlashAttribute(
                "mensagem",
                "Usuário cadastrado com sucesso!");

        return "redirect:/user/logar";
    }

    @GetMapping("/user/logar")
    public String logarForm() {
        return "user/logar";
    }

    @PostMapping("/user/logar")
    public String logar(
            @RequestParam String nome,
            @RequestParam String senha,
            HttpSession session,
            org.springframework.ui.Model model) {

        Optional<User> userOpt = userRepository.findByNomeAndSenha(nome, senha);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (!user.isAtivo()) {
                model.addAttribute("mensagem", "Sua conta foi desativada. Entre em contato com um administrador.");
                return "user/logar";
            }
            session.setAttribute("usuarioLogado", user);
            return "redirect:/dashboard";
        }

        model.addAttribute("mensagem", "Usuário ou senha inválidos.");
        return "user/logar";
    }

    @GetMapping("/user/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("usuarioLogado");
        session.invalidate();
        return "redirect:/user/logar";
    }
}