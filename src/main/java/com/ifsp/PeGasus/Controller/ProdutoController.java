package com.ifsp.PeGasus.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ifsp.PeGasus.Model.Categoria;
import com.ifsp.PeGasus.Model.Produto;
import com.ifsp.PeGasus.Repository.CategoriaRepository;
import com.ifsp.PeGasus.Repository.ProdutoRepository;


@Controller
public class ProdutoController {
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/produto/formulario")
    public String formularioProduto(Model model){
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        model.addAttribute("listaCategorias", listaCategorias);
        return "produto/formularioProduto";
    }

    @PostMapping("/produto/cadastro")
    public String saveProdutos(Produto produto, @RequestParam(required = false) Long categoriaId, @RequestParam("img") MultipartFile img, Model model) {
        
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()
                || produto.getDescricao() == null || produto.getDescricao().trim().isEmpty()
                || categoriaId == null) {
            List<Categoria> listaCategorias = categoriaRepository.findAll();
            model.addAttribute("listaCategorias", listaCategorias);
            model.addAttribute("erro", "Preencha todos os campos obrigatórios.");
            return "produto/formularioProduto";
        }

        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
        produto.setCategoria(categoria);

        if (produto.getCaracteristicas() != null) {
            produto.getCaracteristicas().removeIf(c -> c.getNome() == null || c.getNome().trim().isEmpty());
        }

        if (img != null && !img.isEmpty()) {
            try {
                produto.setImagem(img.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        produtoRepository.save(produto);
        return "redirect:/produto/lista";
    }

    @GetMapping("/produto/lista")
    public String listProdutos(Model model){
        List<Produto> listaProdutos = produtoRepository.findAll();
        model.addAttribute("listaProdutos",listaProdutos);
        return "produto/listaProdutos";
    }

    @GetMapping("/produto/{id}")
    public String produto(Model model, @PathVariable long id){
        Produto produto = produtoRepository.findById(id).orElse(null);
        model.addAttribute("produto",produto);
        return "produto/detalhesProduto";
    }


    @GetMapping("/produto/{id}/editar")
    public String editarProduto(@PathVariable long id, Model model){
        Produto produto = produtoRepository.findById(id).orElse(null);
        List<Categoria> listaCategorias = categoriaRepository.findAll();
        model.addAttribute("produto", produto);
        model.addAttribute("listaCategorias", listaCategorias);
        return "produto/formularioEditarProduto";
    }

    @PostMapping("/produto/atualizar")
    public String atualizarProduto(@RequestParam long id,
                                   @RequestParam(required = false) String descricao,
                                   @RequestParam(required = false) Long preco,
                                   @RequestParam(required = false) Long categoriaId,
                                   @RequestParam(required = false) String nome,
                                   @RequestParam(value = "img", required = false) MultipartFile img,
                                   Produto produtoForm,
                                   Model model){

        if (nome == null || nome.trim().isEmpty()
                || descricao == null || descricao.trim().isEmpty()
                || preco == null || categoriaId == null) {
            Produto produto = produtoRepository.findById(id).orElse(null);
            List<Categoria> listaCategorias = categoriaRepository.findAll();
            model.addAttribute("produto", produto);
            model.addAttribute("listaCategorias", listaCategorias);
            model.addAttribute("erro", "Preencha todos os campos obrigatórios.");
            return "produto/formularioEditarProduto";
        }

        Produto produto = produtoRepository.findById(id).orElse(null);
        Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);

        produto.setDescricao(descricao);
        produto.setPreco(preco);
        produto.setCategoria(categoria);
        produto.setNome(nome);

        if (produtoForm.getCaracteristicas() != null) {
            produtoForm.getCaracteristicas().removeIf(c -> c.getNome() == null || c.getNome().trim().isEmpty());
            produto.setCaracteristicas(produtoForm.getCaracteristicas());
        }

        if (img != null && !img.isEmpty()) {
            try {
                produto.setImagem(img.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        produtoRepository.save(produto);
        
        return "redirect:/produto/lista";
    }

    @GetMapping("/produto/{id}/excluirProduto")
    public String excluirProduto(@PathVariable long id){
        produtoRepository.deleteById(id);
        return "redirect:/produto/lista";
    }

    @GetMapping("/produto/imagem/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> exibirImagem(@PathVariable long id) {
        Produto produto = produtoRepository.findById(id).orElse(null);
        
        if (produto != null && produto.getImagem() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) 
                    .body(produto.getImagem());
        }
        
        return ResponseEntity.notFound().build();
    }

}
