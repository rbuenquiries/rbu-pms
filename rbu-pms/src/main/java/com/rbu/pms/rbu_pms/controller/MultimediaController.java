package com.rbu.pms.rbu_pms.controller;

import com.rbu.pms.rbu_pms.domain.Items;
import com.rbu.pms.rbu_pms.model.MultimediaDTO;
import com.rbu.pms.rbu_pms.repos.ItemsRepository;
import com.rbu.pms.rbu_pms.service.MultimediaService;
import com.rbu.pms.rbu_pms.util.WebUtils;
import jakarta.validation.Valid;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/multimedias")
public class MultimediaController {

    private final MultimediaService multimediaService;
    private final ItemsRepository itemsRepository;

    public MultimediaController(final MultimediaService multimediaService,
            final ItemsRepository itemsRepository) {
        this.multimediaService = multimediaService;
        this.itemsRepository = itemsRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("itemidValues", itemsRepository.findAll(Sort.by("id"))
                .stream()
                .collect(Collectors.toMap(Items::getId, Items::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("multimedias", multimediaService.findAll());
        return "multimedia/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("multimedia") final MultimediaDTO multimediaDTO) {
        return "multimedia/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("multimedia") @Valid final MultimediaDTO multimediaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "multimedia/add";
        }
        multimediaService.create(multimediaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("multimedia.create.success"));
        return "redirect:/multimedias";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("multimedia", multimediaService.get(id));
        return "multimedia/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("multimedia") @Valid final MultimediaDTO multimediaDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "multimedia/edit";
        }
        multimediaService.update(id, multimediaDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("multimedia.update.success"));
        return "redirect:/multimedias";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        multimediaService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("multimedia.delete.success"));
        return "redirect:/multimedias";
    }

}
