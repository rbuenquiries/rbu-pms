package com.rbu.pms.rbu_pms.service;

import com.rbu.pms.rbu_pms.domain.Brands;
import com.rbu.pms.rbu_pms.domain.Categories;
import com.rbu.pms.rbu_pms.model.CategoriesDTO;
import com.rbu.pms.rbu_pms.repos.BrandsRepository;
import com.rbu.pms.rbu_pms.repos.CategoriesRepository;
import com.rbu.pms.rbu_pms.util.NotFoundException;
import com.rbu.pms.rbu_pms.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoriesService {

    private final CategoriesRepository categoriesRepository;
    private final BrandsRepository brandsRepository;

    public CategoriesService(final CategoriesRepository categoriesRepository,
            final BrandsRepository brandsRepository) {
        this.categoriesRepository = categoriesRepository;
        this.brandsRepository = brandsRepository;
    }

    public List<CategoriesDTO> findAll() {
        final List<Categories> categoriess = categoriesRepository.findAll(Sort.by("id"));
        return categoriess.stream()
                .map((categories) -> mapToDTO(categories, new CategoriesDTO()))
                .collect(Collectors.toList());
    }

    public CategoriesDTO get(final Long id) {
        return categoriesRepository.findById(id)
                .map(categories -> mapToDTO(categories, new CategoriesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CategoriesDTO categoriesDTO) {
        final Categories categories = new Categories();
        mapToEntity(categoriesDTO, categories);
        return categoriesRepository.save(categories).getId();
    }

    public void update(final Long id, final CategoriesDTO categoriesDTO) {
        final Categories categories = categoriesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoriesDTO, categories);
        categoriesRepository.save(categories);
    }

    public void delete(final Long id) {
        categoriesRepository.deleteById(id);
    }

    private CategoriesDTO mapToDTO(final Categories categories, final CategoriesDTO categoriesDTO) {
        categoriesDTO.setId(categories.getId());
        categoriesDTO.setName(categories.getName());
        categoriesDTO.setDescription(categories.getDescription());
        categoriesDTO.setIsActive(categories.getIsActive());
        return categoriesDTO;
    }

    private Categories mapToEntity(final CategoriesDTO categoriesDTO, final Categories categories) {
        categories.setName(categoriesDTO.getName());
        categories.setDescription(categoriesDTO.getDescription());
        categories.setIsActive(categoriesDTO.getIsActive());
        return categories;
    }

    public String getReferencedWarning(final Long id) {
        final Categories categories = categoriesRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Brands categoryidBrands = brandsRepository.findFirstByCategoryid(categories);
        if (categoryidBrands != null) {
            return WebUtils.getMessage("categories.brands.categoryid.referenced", categoryidBrands.getId());
        }
        return null;
    }

}
