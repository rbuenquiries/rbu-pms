package com.rbu.pms.rbu_pms.rest;

import com.rbu.pms.rbu_pms.model.OffersDTO;
import com.rbu.pms.rbu_pms.service.OffersService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/offerss", produces = MediaType.APPLICATION_JSON_VALUE)
public class OffersResource {

    private final OffersService offersService;

    public OffersResource(final OffersService offersService) {
        this.offersService = offersService;
    }

    @GetMapping
    public ResponseEntity<List<OffersDTO>> getAllOfferss() {
        return ResponseEntity.ok(offersService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OffersDTO> getOffers(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(offersService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createOffers(@RequestBody @Valid final OffersDTO offersDTO) {
        return new ResponseEntity<>(offersService.create(offersDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOffers(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final OffersDTO offersDTO) {
        offersService.update(id, offersDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteOffers(@PathVariable(name = "id") final Long id) {
        offersService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
