package com.ifsp.PeGasus.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ifsp.PeGasus.Model.Produto;
import com.ifsp.PeGasus.Repository.ProdutoRepository;


@Controller
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("formularioProduto")
    public String formularioProduto(){
        return "formularioProduto";
    }

    @PostMapping("/produtos")
    public String saveProdutos(@RequestParam String nome, @RequestParam String descricao, @RequestParam long preco){
        produtoRepository.save(new Produto(nome, descricao, preco));
        return "redirect:/formularioProduto";
    }

    @GetMapping("/listaProdutos")
    public String listProdutos(Model model){
        List<Produto> listaProdutos = produtoRepository.findAll();
        model.addAttribute("listaProdutos",listaProdutos);
        return "listaProdutos";
    }

    @GetMapping("/produto/{id}")
    public String produto(Model model, @PathVariable long id){
        Produto produto = produtoRepository.findByID(id);
        model.addAttribute("produto",produto);
        return "detalhesProduto";
    }


    @GetMapping("/produto/{id}/editar")
    public String editarProduto(@PathVariable long id, Model model){
        Produto produto = produtoRepository.findByID(id);
        model.addAttribute("produto", produto);
        return "formularioEditarProduto";
    }

    @PostMapping("/produto/atualizarProduto")
    public String atualizarProduto(@RequestParam long id,
                                   @RequestParam String descricao,
                                   @RequestParam long preco,
                                    @RequestParam String nome){

        Produto produto = produtoRepository.findByID(id);
        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setNome(nome);
        produtoRepository.update(produto);
        return "redirect:/listaProdutos";
    }

    @GetMapping("/produto/{id}/excluirProduto")
    public String excluirProduto(@PathVariable long id){
        produtoRepository.delete(id);
        return "redirect:/listaProdutos";
    }

}
