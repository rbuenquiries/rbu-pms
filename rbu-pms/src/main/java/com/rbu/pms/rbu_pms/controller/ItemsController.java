package com.rbu.pms.rbu_pms.controller;

import com.rbu.pms.rbu_pms.domain.Brands;
import com.rbu.pms.rbu_pms.model.ItemsDTO;
import com.rbu.pms.rbu_pms.repos.BrandsRepository;
import com.rbu.pms.rbu_pms.service.ItemsService;
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
@RequestMapping("/itemss")
public class ItemsController {

    private final ItemsService itemsService;
    private final BrandsRepository brandsRepository;

    public ItemsController(final ItemsService itemsService,
            final BrandsRepository brandsRepository) {
        this.itemsService = itemsService;
        this.brandsRepository = brandsRepository;
    }

    @ModelAttribute
    public void prepareContext(final Model model) {
        model.addAttribute("brandidValues", brandsRepository.findAll(Sort.by("id"))
                .stream()
                .collect(Collectors.toMap(Brands::getId, Brands::getName)));
    }

    @GetMapping
    public String list(final Model model) {
        model.addAttribute("itemss", itemsService.findAll());
        return "items/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("items") final ItemsDTO itemsDTO) {
        return "items/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("items") @Valid final ItemsDTO itemsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "items/add";
        }
        itemsService.create(itemsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("items.create.success"));
        return "redirect:/itemss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("items", itemsService.get(id));
        return "items/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("items") @Valid final ItemsDTO itemsDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "items/edit";
        }
        itemsService.update(id, itemsDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("items.update.success"));
        return "redirect:/itemss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        final String referencedWarning = itemsService.getReferencedWarning(id);
        if (referencedWarning != null) {
            redirectAttributes.addFlashAttribute(WebUtils.MSG_ERROR, referencedWarning);
        } else {
            itemsService.delete(id);
            redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("items.delete.success"));
        }
        return "redirect:/itemss";
    }

}
