package com.rbu.pms.rbu_pms.controller;

import com.rbu.pms.rbu_pms.model.CategoriesDTO;
import com.rbu.pms.rbu_pms.service.CategoriesService;
import com.rbu.pms.rbu_pms.util.WebUtils;
import jakarta.validation.Valid;
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
@RequestMapping("/categoriess")
public class CategoriesController {

    private final CategoriesService categoriesService;

    public CategoriesController(final CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("categoriess", categoriesService.findAll());
        return "categories/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("categories") final CategoriesDTO categoriesDTO) {
        return "categories/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("categories") @Valid final CategoriesDTO categoriesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "categories/add";
        }
        categoriesService.create(categoriesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("categories.create.success"));
        return "redirect:/categoriess";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("categories", categoriesService.get(id));
        return "categories/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("categories") @Valid final CategoriesDTO categoriesDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "categories/edit";
        }
        categoriesService.update(id, categoriesDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("categories.update.success"));
        return "redirect:/categoriess";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = categoriesService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            categoriesService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("categories.delete.success"));
        }
        return "redirect:/categoriess";
    }

}
