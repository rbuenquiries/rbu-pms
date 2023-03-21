package com.rbu.pms.rbu_pms.controller;

import com.rbu.pms.rbu_pms.domain.Categories;
import com.rbu.pms.rbu_pms.model.BrandsDTO;
import com.rbu.pms.rbu_pms.repos.CategoriesRepository;
import com.rbu.pms.rbu_pms.service.BrandsService;
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
@RequestMapping("/brandss")
public class BrandsController {

    private final BrandsService brandsService;
    private final CategoriesRepository categoriesRepository;

    public BrandsController(final BrandsService brandsService,
            final CategoriesRepository categoriesRepository) {
        this.brandsService = brandsService;
        this.categoriesRepository = categoriesRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("categoryidValues", categoriesRepository.findAll(Sort.by("id"))
                .stream()
                .collect(Collectors.toMap(Categories::getId, Categories::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("brandss", brandsService.findAll());
        return "brands/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("brands") final BrandsDTO brandsDTO) {
        return "brands/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("brands") @Valid final BrandsDTO brandsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "brands/add";
        }
        brandsService.create(brandsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("brands.create.success"));
        return "redirect:/brandss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("brands", brandsService.get(id));
        return "brands/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("brands") @Valid final BrandsDTO brandsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "brands/edit";
        }
        brandsService.update(id, brandsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("brands.update.success"));
        return "redirect:/brandss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = brandsService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            brandsService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("brands.delete.success"));
        }
        return "redirect:/brandss";
    }

}
