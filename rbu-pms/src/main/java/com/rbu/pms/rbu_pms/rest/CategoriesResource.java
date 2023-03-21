package com.rbu.pms.rbu_pms.rest;

import com.rbu.pms.rbu_pms.model.CategoriesDTO;
import com.rbu.pms.rbu_pms.service.CategoriesService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping(value = "/api/categoriess", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriesResource {

    private final CategoriesService categoriesService;

    public CategoriesResource(final CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriesDTO>> getAllCategoriess() {
        return ResponseEntity.ok(categoriesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDTO> getCategories(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(categoriesService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createCategories(
            @RequestBody @Valid final CategoriesDTO categoriesDTO) {
        return new ResponseEntity<>(categoriesService.create(categoriesDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategories(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CategoriesDTO categoriesDTO) {
        categoriesService.update(id, categoriesDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCategories(@PathVariable(name = "id") final Long id) {
        categoriesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
