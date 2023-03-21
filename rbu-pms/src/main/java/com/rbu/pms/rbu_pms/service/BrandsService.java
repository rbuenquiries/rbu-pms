package com.rbu.pms.rbu_pms.service;

import com.rbu.pms.rbu_pms.domain.Brands;
import com.rbu.pms.rbu_pms.domain.Categories;
import com.rbu.pms.rbu_pms.domain.Items;
import com.rbu.pms.rbu_pms.model.BrandsDTO;
import com.rbu.pms.rbu_pms.repos.BrandsRepository;
import com.rbu.pms.rbu_pms.repos.CategoriesRepository;
import com.rbu.pms.rbu_pms.repos.ItemsRepository;
import com.rbu.pms.rbu_pms.util.NotFoundException;
import com.rbu.pms.rbu_pms.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BrandsService {

    private final BrandsRepository brandsRepository;
    private final CategoriesRepository categoriesRepository;
    private final ItemsRepository itemsRepository;

    public BrandsService(final BrandsRepository brandsRepository,
            final CategoriesRepository categoriesRepository,
            final ItemsRepository itemsRepository) {
        this.brandsRepository = brandsRepository;
        this.categoriesRepository = categoriesRepository;
        this.itemsRepository = itemsRepository;
    }

    public List<BrandsDTO> findAll() {
        final List<Brands> brandss = brandsRepository.findAll(Sort.by("id"));
        return brandss.stream()
                .map((brands) -> mapToDTO(brands, new BrandsDTO()))
                .collect(Collectors.toList());
    }

    public BrandsDTO get(final Long id) {
        return brandsRepository.findById(id)
                .map(brands -> mapToDTO(brands, new BrandsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BrandsDTO brandsDTO) {
        final Brands brands = new Brands();
        mapToEntity(brandsDTO, brands);
        return brandsRepository.save(brands).getId();
    }

    public void update(final Long id, final BrandsDTO brandsDTO) {
        final Brands brands = brandsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(brandsDTO, brands);
        brandsRepository.save(brands);
    }

    public void delete(final Long id) {
        brandsRepository.deleteById(id);
    }

    private BrandsDTO mapToDTO(final Brands brands, final BrandsDTO brandsDTO) {
        brandsDTO.setId(brands.getId());
        brandsDTO.setName(brands.getName());
        brandsDTO.setDescription(brands.getDescription());
        brandsDTO.setIsactive(brands.getIsactive());
        brandsDTO.setCategoryid(brands.getCategoryid() == null ? null : brands.getCategoryid().getId());
        return brandsDTO;
    }

    private Brands mapToEntity(final BrandsDTO brandsDTO, final Brands brands) {
        brands.setName(brandsDTO.getName());
        brands.setDescription(brandsDTO.getDescription());
        brands.setIsactive(brandsDTO.getIsactive());
        final Categories categoryid = brandsDTO.getCategoryid() == null ? null : categoriesRepository.findById(brandsDTO.getCategoryid())
                .orElseThrow(() -> new NotFoundException("categoryid not found"));
        brands.setCategoryid(categoryid);
        return brands;
    }

    public String getReferencedWarning(final Long id) {
        final Brands brands = brandsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Items brandidItems = itemsRepository.findFirstByBrandid(brands);
        if (brandidItems != null) {
            return WebUtils.getMessage("brands.items.brandid.referenced", brandidItems.getId());
        }
        return null;
    }

}
