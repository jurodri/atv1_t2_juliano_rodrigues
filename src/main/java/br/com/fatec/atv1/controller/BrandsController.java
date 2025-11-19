package br.com.fatec.atv1.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/brands", produces = MediaType.APPLICATION_JSON_VALUE)
public class BrandsController {

    
    private final Map<Integer, Map<String, String>> brands = new HashMap<>(Map.of(
        1, Map.of("name", "Xiaomi", "country", "China", "founded", "2010"),
        2, Map.of("name", "Samsung", "country", "South Korea", "founded", "1938"),
        3, Map.of("name", "Jovi", "country", "Brazil", "founded", "2025")
    ));

    // GET ALL
    @GetMapping
    public ResponseEntity<Map<Integer, Map<String, String>>> getBrands() {
        return ResponseEntity.ok(brands);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getBrandById(@PathVariable Integer id) {

        Map<String, String> brand = brands.get(id);

        if (brand == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of(
                    "message", "Marca não encontrada",
                    "id", id.toString()
                ));
        }

        return ResponseEntity.ok(
            Map.of(
                "id", id.toString(),
                "name", brand.get("name"),
                "country", brand.get("country"),
                "founded", brand.get("founded")
            )
        );
    }

    // POST - ADD BRAND
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addBrand(
            @RequestBody Map<String, String> requestBrand) {

        Integer cod = Integer.parseInt(requestBrand.get("id"));
        String name = requestBrand.get("name");
        String country = requestBrand.get("country");
        String founded = requestBrand.get("founded");

        brands.put(cod, Map.of(
            "name", name,
            "country", country,
            "founded", founded
        ));

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(Map.of(
                "id", cod,
                "name", name,
                "country", country,
                "founded", founded,
                "message", "Marca adicionada com sucesso"
            ));
    }
}