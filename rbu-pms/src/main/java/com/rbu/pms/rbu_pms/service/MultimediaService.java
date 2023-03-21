package com.rbu.pms.rbu_pms.service;

import com.rbu.pms.rbu_pms.domain.Items;
import com.rbu.pms.rbu_pms.domain.Multimedia;
import com.rbu.pms.rbu_pms.model.MultimediaDTO;
import com.rbu.pms.rbu_pms.repos.ItemsRepository;
import com.rbu.pms.rbu_pms.repos.MultimediaRepository;
import com.rbu.pms.rbu_pms.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MultimediaService {

    private final MultimediaRepository multimediaRepository;
    private final ItemsRepository itemsRepository;

    public MultimediaService(final MultimediaRepository multimediaRepository,
            final ItemsRepository itemsRepository) {
        this.multimediaRepository = multimediaRepository;
        this.itemsRepository = itemsRepository;
    }

    public List<MultimediaDTO> findAll() {
        final List<Multimedia> multimedias = multimediaRepository.findAll(Sort.by("id"));
        return multimedias.stream()
                .map((multimedia) -> mapToDTO(multimedia, new MultimediaDTO()))
                .collect(Collectors.toList());
    }

    public MultimediaDTO get(final Long id) {
        return multimediaRepository.findById(id)
                .map(multimedia -> mapToDTO(multimedia, new MultimediaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MultimediaDTO multimediaDTO) {
        final Multimedia multimedia = new Multimedia();
        mapToEntity(multimediaDTO, multimedia);
        return multimediaRepository.save(multimedia).getId();
    }

    public void update(final Long id, final MultimediaDTO multimediaDTO) {
        final Multimedia multimedia = multimediaRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(multimediaDTO, multimedia);
        multimediaRepository.save(multimedia);
    }

    public void delete(final Long id) {
        multimediaRepository.deleteById(id);
    }

    private MultimediaDTO mapToDTO(final Multimedia multimedia, final MultimediaDTO multimediaDTO) {
        multimediaDTO.setId(multimedia.getId());
        multimediaDTO.setPrimary(multimedia.getPrimary());
        multimediaDTO.setSecondary(multimedia.getSecondary());
        multimediaDTO.setIsactive(multimedia.getIsactive());
        multimediaDTO.setItemid(multimedia.getItemid() == null ? null : multimedia.getItemid().getId());
        return multimediaDTO;
    }

    private Multimedia mapToEntity(final MultimediaDTO multimediaDTO, final Multimedia multimedia) {
        multimedia.setPrimary(multimediaDTO.getPrimary());
        multimedia.setSecondary(multimediaDTO.getSecondary());
        multimedia.setIsactive(multimediaDTO.getIsactive());
        final Items itemid = multimediaDTO.getItemid() == null ? null : itemsRepository.findById(multimediaDTO.getItemid())
                .orElseThrow(() -> new NotFoundException("itemid not found"));
        multimedia.setItemid(itemid);
        return multimedia;
    }

}
