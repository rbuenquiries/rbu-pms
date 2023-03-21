package com.rbu.pms.rbu_pms.rest;

import com.rbu.pms.rbu_pms.model.BrandsDTO;
import com.rbu.pms.rbu_pms.service.BrandsService;
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
@RequestMapping(value = "/api/brandss", produces = MediaType.APPLICATION_JSON_VALUE)
public class BrandsResource {

    private final BrandsService brandsService;

    public BrandsResource(final BrandsService brandsService) {
        this.brandsService = brandsService;
    }

    @GetMapping
    public ResponseEntity<List<BrandsDTO>> getAllBrandss() {
        return ResponseEntity.ok(brandsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandsDTO> getBrands(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(brandsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createBrands(@RequestBody @Valid final BrandsDTO brandsDTO) {
        return new ResponseEntity<>(brandsService.create(brandsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBrands(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final BrandsDTO brandsDTO) {
        brandsService.update(id, brandsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteBrands(@PathVariable(name = "id") final Long id) {
        brandsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
