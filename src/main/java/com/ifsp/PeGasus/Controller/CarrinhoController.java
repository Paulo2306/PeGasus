package com.ifsp.PeGasus.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.PeGasus.Model.Carrinho;
import com.ifsp.PeGasus.Model.Cupom;
import com.ifsp.PeGasus.Model.Produto;
import com.ifsp.PeGasus.Model.User;
import com.ifsp.PeGasus.Repository.CarrinhoRepository;
import com.ifsp.PeGasus.Repository.CupomRepository;
import com.ifsp.PeGasus.Repository.ProdutoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CupomRepository cupomRepository;

    @GetMapping("/carrinho/mostrar")
    public String mostrarCarrinho(Model model, HttpSession session){
        User user = (User) session.getAttribute("usuarioLogado");
        if (user == null) {
            return "redirect:/user/logar";
        }

        Carrinho carrinho = carrinhoRepository.findByIdUser(user.getId())
            .orElseGet(() -> {
                Carrinho newCarrinho = new Carrinho(user.getId());
                newCarrinho.setProdutos(new ArrayList<>());
                return carrinhoRepository.save(newCarrinho);
        });

        model.addAttribute("subtotal", carrinho.getSubtotal());
        model.addAttribute("desconto", carrinho.getValorDesconto());
        model.addAttribute("total", carrinho.getTotal());
        model.addAttribute("cupomAtivo", carrinho.getCupom());
        model.addAttribute("listaProdutos", carrinho.getProdutos());

        String msgSucesso = (String) session.getAttribute("msgSucesso");
        if (msgSucesso != null) {
            model.addAttribute("msgSucesso", msgSucesso);
            session.removeAttribute("msgSucesso");
        }
        String msgCupom = (String) session.getAttribute("msgCupom");
        if (msgCupom != null) {
            model.addAttribute("msgCupom", msgCupom);
            session.removeAttribute("msgCupom");
        }
        String erroCupom = (String) session.getAttribute("erroCupom");
        if (erroCupom != null) {
            model.addAttribute("erroCupom", erroCupom);
            session.removeAttribute("erroCupom");
        }

        return "carrinho/mostrar";
    }

    @PostMapping("/carrinho/aplicarCupom")
    public String aplicarCupom(@RequestParam String nomeCupom, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");
        if (user == null) return "redirect:/user/logar";

        Carrinho carrinho = carrinhoRepository.findByIdUser(user.getId()).orElse(null);
        if (carrinho == null) return "redirect:/carrinho/mostrar";

        Cupom cupom = cupomRepository.findByNomeIgnoreCase(nomeCupom.trim()).orElse(null);

        if (cupom != null) {
            carrinho.setCupom(cupom);
            carrinhoRepository.save(carrinho);
            session.setAttribute("msgCupom", "Cupom '" + cupom.getNome().toUpperCase() + "' aplicado com sucesso!");
        } else {
            session.setAttribute("erroCupom", "Cupom inválido ou expirado.");
        }

        return "redirect:/carrinho/mostrar";
    }

    @PostMapping("/carrinho/adicionar")
    public String saveCarrinho(@RequestParam long produtoId, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");
        if (user == null) {
            return "redirect:/user/logar";
        }

        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto == null) {
            return "redirect:/dashboard";
        }

        Carrinho carrinho = carrinhoRepository.findByIdUser(user.getId())
            .orElseGet(() -> {
                Carrinho newCarrinho = new Carrinho(user.getId());
                newCarrinho.setProdutos(new ArrayList<>());
                return newCarrinho;
            });

        if (carrinho.getProdutos() == null) {
            carrinho.setProdutos(new ArrayList<>());
        }
        carrinho.getProdutos().add(produto);
        carrinhoRepository.save(carrinho);

        return "redirect:/carrinho/mostrar";
    }

    @PostMapping("/carrinho/limpar")
    public String limparCarrinho(HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");
        if (user == null) return "redirect:/user/logar";

        Carrinho carrinho = carrinhoRepository.findByIdUser(user.getId()).orElse(null);
        if (carrinho != null) {
            carrinho.getProdutos().clear();
            carrinho.setCupom(null);
            carrinhoRepository.save(carrinho);
            session.setAttribute("msgSucesso", "Carrinho esvaziado com sucesso!");
        }

        return "redirect:/carrinho/mostrar";
    }

    @PostMapping("/carrinho/comprar")
    public String comprarCarrinho(HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");
        if (user == null) return "redirect:/user/logar";

        Carrinho carrinho = carrinhoRepository.findByIdUser(user.getId()).orElse(null);
        if (carrinho == null || carrinho.getProdutos() == null || carrinho.getProdutos().isEmpty()) {
            session.setAttribute("erroCupom", "Não é possível finalizar a compra com o carrinho vazio.");
            return "redirect:/carrinho/mostrar";
        }

        carrinho.getProdutos().clear();
        carrinho.setCupom(null);
        carrinhoRepository.save(carrinho);
        session.setAttribute("msgSucesso", "Compra realizada com sucesso!");

        return "redirect:/carrinho/mostrar";
    }
}
