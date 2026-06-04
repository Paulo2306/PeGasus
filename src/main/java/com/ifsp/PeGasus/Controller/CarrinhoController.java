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
import com.ifsp.PeGasus.Model.Produto;
import com.ifsp.PeGasus.Model.User;
import com.ifsp.PeGasus.Repository.CarrinhoRepository;
import com.ifsp.PeGasus.Repository.ProdutoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

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

        model.addAttribute("listaProdutos", carrinho.getProdutos());
        return "carrinho/mostrar";
    }

    @PostMapping("/carrinho/adicionar")
    public String saveCarrinho(@RequestParam long produtoId, HttpSession session) {
        User user = (User) session.getAttribute("usuarioLogado");
        if (user == null) {
            return "redirect:/user/logar";
        }

        Produto produto = produtoRepository.findById(produtoId).orElse(null);
        if (produto == null) {
            return "redirect:/produto/lista";
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
}
