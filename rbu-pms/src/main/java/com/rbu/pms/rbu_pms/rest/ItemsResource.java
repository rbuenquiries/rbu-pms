package com.rbu.pms.rbu_pms.rest;

import com.rbu.pms.rbu_pms.model.ItemsDTO;
import com.rbu.pms.rbu_pms.service.ItemsService;
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
@RequestMapping(value = "/api/itemss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemsResource {

    private final ItemsService itemsService;

    public ItemsResource(final ItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @GetMapping
    public ResponseEntity<List<ItemsDTO>> getAllItemss() {
        return ResponseEntity.ok(itemsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemsDTO> getItems(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(itemsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createItems(@RequestBody @Valid final ItemsDTO itemsDTO) {
        return new ResponseEntity<>(itemsService.create(itemsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateItems(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final ItemsDTO itemsDTO) {
        itemsService.update(id, itemsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteItems(@PathVariable(name = "id") final Long id) {
        itemsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
