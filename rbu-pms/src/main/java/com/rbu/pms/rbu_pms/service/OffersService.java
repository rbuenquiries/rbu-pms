package com.rbu.pms.rbu_pms.service;

import com.rbu.pms.rbu_pms.domain.Items;
import com.rbu.pms.rbu_pms.domain.Offers;
import com.rbu.pms.rbu_pms.model.OffersDTO;
import com.rbu.pms.rbu_pms.repos.ItemsRepository;
import com.rbu.pms.rbu_pms.repos.OffersRepository;
import com.rbu.pms.rbu_pms.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OffersService {

    private final OffersRepository offersRepository;
    private final ItemsRepository itemsRepository;

    public OffersService(final OffersRepository offersRepository,
            final ItemsRepository itemsRepository) {
        this.offersRepository = offersRepository;
        this.itemsRepository = itemsRepository;
    }

    public List<OffersDTO> findAll() {
        final List<Offers> offerss = offersRepository.findAll(Sort.by("id"));
        return offerss.stream()
                .map((offers) -> mapToDTO(offers, new OffersDTO()))
                .collect(Collectors.toList());
    }

    public OffersDTO get(final Long id) {
        return offersRepository.findById(id)
                .map(offers -> mapToDTO(offers, new OffersDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OffersDTO offersDTO) {
        final Offers offers = new Offers();
        mapToEntity(offersDTO, offers);
        return offersRepository.save(offers).getId();
    }

    public void update(final Long id, final OffersDTO offersDTO) {
        final Offers offers = offersRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(offersDTO, offers);
        offersRepository.save(offers);
    }

    public void delete(final Long id) {
        offersRepository.deleteById(id);
    }

    private OffersDTO mapToDTO(final Offers offers, final OffersDTO offersDTO) {
        offersDTO.setId(offers.getId());
        offersDTO.setOffername(offers.getOffername());
        offersDTO.setExpdate(offers.getExpdate());
        offersDTO.setCouponcode(offers.getCouponcode());
        offersDTO.setPercentage(offers.getPercentage());
        offersDTO.setMaximum(offers.getMaximum());
        offersDTO.setItemid(offers.getItemid() == null ? null : offers.getItemid().getId());
        return offersDTO;
    }

    private Offers mapToEntity(final OffersDTO offersDTO, final Offers offers) {
        offers.setOffername(offersDTO.getOffername());
        offers.setExpdate(offersDTO.getExpdate());
        offers.setCouponcode(offersDTO.getCouponcode());
        offers.setPercentage(offersDTO.getPercentage());
        offers.setMaximum(offersDTO.getMaximum());
        final Items itemid = offersDTO.getItemid() == null ? null : itemsRepository.findById(offersDTO.getItemid())
                .orElseThrow(() -> new NotFoundException("itemid not found"));
        offers.setItemid(itemid);
        return offers;
    }

}
