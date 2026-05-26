package com.ifsp.PeGasus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.PeGasus.Model.Carrinho;
import com.ifsp.PeGasus.Model.Categoria;
import com.ifsp.PeGasus.Model.Produto;
import com.ifsp.PeGasus.Repository.CarrinhoRepository;

@Controller
public class CarrinhoController {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @GetMapping("/carrinho/mostrar")
    public String mostrarCarrinho(Model model, Carrinho carrinho){
        List<Produto> listaProdutos = carrinho.getProdutos();
        model.addAttribute("listaProduto", listaProdutos);
        return "carrinho/mostrar";
    }

    @PostMapping("/carrinho/adicionar")
    public String saveCarrinho() {
        return "";
    }
}
