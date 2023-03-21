package com.rbu.pms.rbu_pms.controller;

import com.rbu.pms.rbu_pms.domain.Items;
import com.rbu.pms.rbu_pms.model.OffersDTO;
import com.rbu.pms.rbu_pms.repos.ItemsRepository;
import com.rbu.pms.rbu_pms.service.OffersService;
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
@RequestMapping("/offerss")
public class OffersController {

    private final OffersService offersService;
    private final ItemsRepository itemsRepository;

    public OffersController(final OffersService offersService,
            final ItemsRepository itemsRepository) {
        this.offersService = offersService;
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
        model.addAttribute("offerss", offersService.findAll());
        return "offers/list";
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("offers") final OffersDTO offersDTO) {
        return "offers/add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("offers") @Valid final OffersDTO offersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "offers/add";
        }
        offersService.create(offersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("offers.create.success"));
        return "redirect:/offerss";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable final Long id, final Model model) {
        model.addAttribute("offers", offersService.get(id));
        return "offers/edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable final Long id,
            @ModelAttribute("offers") @Valid final OffersDTO offersDTO,
            final BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "offers/edit";
        }
        offersService.update(id, offersDTO);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_SUCCESS, WebUtils.getMessage("offers.update.success"));
        return "redirect:/offerss";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable final Long id, final RedirectAttributes redirectAttributes) {
        offersService.delete(id);
        redirectAttributes.addFlashAttribute(WebUtils.MSG_INFO, WebUtils.getMessage("offers.delete.success"));
        return "redirect:/offerss";
    }

}
