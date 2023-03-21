package com.rbu.pms.rbu_pms.service;

import com.rbu.pms.rbu_pms.domain.Brands;
import com.rbu.pms.rbu_pms.domain.Items;
import com.rbu.pms.rbu_pms.domain.Multimedia;
import com.rbu.pms.rbu_pms.domain.Offers;
import com.rbu.pms.rbu_pms.model.ItemsDTO;
import com.rbu.pms.rbu_pms.repos.BrandsRepository;
import com.rbu.pms.rbu_pms.repos.ItemsRepository;
import com.rbu.pms.rbu_pms.repos.MultimediaRepository;
import com.rbu.pms.rbu_pms.repos.OffersRepository;
import com.rbu.pms.rbu_pms.util.NotFoundException;
import com.rbu.pms.rbu_pms.util.WebUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ItemsService {

    private final ItemsRepository itemsRepository;
    private final BrandsRepository brandsRepository;
    private final MultimediaRepository multimediaRepository;
    private final OffersRepository offersRepository;

    public ItemsService(final ItemsRepository itemsRepository,
            final BrandsRepository brandsRepository,
            final MultimediaRepository multimediaRepository,
            final OffersRepository offersRepository) {
        this.itemsRepository = itemsRepository;
        this.brandsRepository = brandsRepository;
        this.multimediaRepository = multimediaRepository;
        this.offersRepository = offersRepository;
    }

    public List<ItemsDTO> findAll() {
        final List<Items> itemss = itemsRepository.findAll(Sort.by("id"));
        return itemss.stream()
                .map((items) -> mapToDTO(items, new ItemsDTO()))
                .collect(Collectors.toList());
    }

    public ItemsDTO get(final Long id) {
        return itemsRepository.findById(id)
                .map(items -> mapToDTO(items, new ItemsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ItemsDTO itemsDTO) {
        final Items items = new Items();
        mapToEntity(itemsDTO, items);
        return itemsRepository.save(items).getId();
    }

    public void update(final Long id, final ItemsDTO itemsDTO) {
        final Items items = itemsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(itemsDTO, items);
        itemsRepository.save(items);
    }

    public void delete(final Long id) {
        itemsRepository.deleteById(id);
    }

    private ItemsDTO mapToDTO(final Items items, final ItemsDTO itemsDTO) {
        itemsDTO.setId(items.getId());
        itemsDTO.setName(items.getName());
        itemsDTO.setCode(items.getCode());
        itemsDTO.setPrice(items.getPrice());
        itemsDTO.setDescription(items.getDescription());
        itemsDTO.setColor(items.getColor());
        itemsDTO.setWeight(items.getWeight());
        itemsDTO.setIsactive(items.getIsactive());
        itemsDTO.setBrandid(items.getBrandid() == null ? null : items.getBrandid().getId());
        return itemsDTO;
    }

    private Items mapToEntity(final ItemsDTO itemsDTO, final Items items) {
        items.setName(itemsDTO.getName());
        items.setCode(itemsDTO.getCode());
        items.setPrice(itemsDTO.getPrice());
        items.setDescription(itemsDTO.getDescription());
        items.setColor(itemsDTO.getColor());
        items.setWeight(itemsDTO.getWeight());
        items.setIsactive(itemsDTO.getIsactive());
        final Brands brandid = itemsDTO.getBrandid() == null ? null : brandsRepository.findById(itemsDTO.getBrandid())
                .orElseThrow(() -> new NotFoundException("brandid not found"));
        items.setBrandid(brandid);
        return items;
    }

    public String getReferencedWarning(final Long id) {
        final Items items = itemsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Multimedia itemidMultimedia = multimediaRepository.findFirstByItemid(items);
        if (itemidMultimedia != null) {
            return WebUtils.getMessage("items.multimedia.itemid.referenced", itemidMultimedia.getId());
        }
        final Offers itemidOffers = offersRepository.findFirstByItemid(items);
        if (itemidOffers != null) {
            return WebUtils.getMessage("items.offers.itemid.referenced", itemidOffers.getId());
        }
        return null;
    }

}
