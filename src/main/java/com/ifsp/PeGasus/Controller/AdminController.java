package com.ifsp.PeGasus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ifsp.PeGasus.Enum.Tipo;
import com.ifsp.PeGasus.Model.User;
import com.ifsp.PeGasus.Repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin/painel")
    public String painelAdmin(HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");
        if (user == null || user.getTipo() != Tipo.ADMIN) {
            return "redirect:/dashboard";
        }
        return "admin/painel";
    }

    @GetMapping("/admin/usuarios")
    public String listarUsuarios(Model model, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");
        if (user == null || user.getTipo() != Tipo.ADMIN) {
            return "redirect:/dashboard";
        }

        List<User> listaUsuarios = userRepository.findAll();
        model.addAttribute("listaUsuarios", listaUsuarios);
        model.addAttribute("usuarioLogadoId", user.getId());
        return "admin/listaUsuarios";
    }

    @GetMapping("/admin/usuarios/{id}/editar")
    public String editarUsuarioForm(@PathVariable long id, Model model, HttpSession session) {
        User logado = (User) session.getAttribute("usuarioLogado");
        if (logado == null || logado.getTipo() != Tipo.ADMIN) {
            return "redirect:/dashboard";
        }

        User usuario = userRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/admin/usuarios";
        }

        model.addAttribute("usuario", usuario);
        return "admin/formularioEditarUsuario";
    }

    @PostMapping("/admin/usuarios/atualizar")
    public String atualizarUsuario(
            @RequestParam long id,
            @RequestParam String nome,
            @RequestParam String tipoUsuario,
            @RequestParam(required = false) Boolean ativo,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        User logado = (User) session.getAttribute("usuarioLogado");
        if (logado == null || logado.getTipo() != Tipo.ADMIN) {
            return "redirect:/dashboard";
        }

        User usuario = userRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/admin/usuarios";
        }

        usuario.setNome(nome);
        usuario.setTipo(Tipo.valueOf(tipoUsuario));
        usuario.setAtivo(ativo != null && ativo);

        userRepository.save(usuario);

        redirectAttributes.addFlashAttribute("msgSucesso", "Usuário '" + nome + "' atualizado com sucesso!");
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/admin/usuarios/{id}/desativar")
    public String desativarUsuario(@PathVariable long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User logado = (User) session.getAttribute("usuarioLogado");
        if (logado == null || logado.getTipo() != Tipo.ADMIN) {
            return "redirect:/dashboard";
        }

        if (logado.getId() == id) {
            redirectAttributes.addFlashAttribute("msgErro", "Você não pode desativar sua própria conta.");
            return "redirect:/admin/usuarios";
        }

        User usuario = userRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setAtivo(false);
            userRepository.save(usuario);
            redirectAttributes.addFlashAttribute("msgSucesso", "Usuário '" + usuario.getNome() + "' desativado com sucesso.");
        }

        return "redirect:/admin/usuarios";
    }

    @GetMapping("/admin/usuarios/{id}/reativar")
    public String reativarUsuario(@PathVariable long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User logado = (User) session.getAttribute("usuarioLogado");
        if (logado == null || logado.getTipo() != Tipo.ADMIN) {
            return "redirect:/dashboard";
        }

        User usuario = userRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setAtivo(true);
            userRepository.save(usuario);
            redirectAttributes.addFlashAttribute("msgSucesso", "Usuário '" + usuario.getNome() + "' reativado com sucesso.");
        }

        return "redirect:/admin/usuarios";
    }
}
