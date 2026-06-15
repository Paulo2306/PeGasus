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

import com.ifsp.PeGasus.Model.Cupom;
import com.ifsp.PeGasus.Repository.CupomRepository;

@Controller
public class CupomController {
    @Autowired
    private CupomRepository cupomRepository;

    @GetMapping("/cupom/formulario")
    public String formulariocupom(Model model){
        List<Cupom> listacupoms = cupomRepository.findAll();
        model.addAttribute("listacupoms", listacupoms);
        return "cupom/formularioCupom";
    }

    @PostMapping("/cupom/cadastro")
    public String savecupoms(@RequestParam String nome, @RequestParam int valorPor){

        cupomRepository.save(new Cupom(nome, valorPor));
        return "redirect:/cupom/formulario";
    }

    @GetMapping("/cupom/lista")
    public String listcupoms(Model model){
        List<Cupom> listaCupoms = cupomRepository.findAll();
        model.addAttribute("listaCupoms",listaCupoms);
        return "cupom/listaCupom";
    }


    @GetMapping("/cupom/{id}/editar")
    public String editarcupom(@PathVariable long id, Model model){
        Cupom cupom = cupomRepository.findById(id).orElse(null);
        model.addAttribute("cupom", cupom);
        return "cupom/formularioEditarCupom";
    }

    @PostMapping("/cupom/atualizar")
    public String atualizarcupom(@RequestParam long id, @RequestParam String nome){

        Cupom cupom = cupomRepository.findById(id).orElse(null);
        cupom.setNome(nome);
        cupomRepository.save(cupom);
        return "redirect:/cupom/lista";
    }

    @GetMapping("/cupom/{id}/excluirCupom")
    public String excluircupom(@PathVariable long id, RedirectAttributes redirectAttributes){
        try {
            cupomRepository.deleteById(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este cupom pois ele está aplicado no carrinho de um cliente.");
        }
        return "redirect:/cupom/lista";
    } 
}
