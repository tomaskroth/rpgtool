package com.tk.rpg.rest;

import com.tk.rpg.domain.Item;
import com.tk.rpg.dto.StoreDefinition;
import com.tk.rpg.service.GenerateStore;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/store")
@RequiredArgsConstructor
public class StoreGenerator {

    private final GenerateStore generateStore;

    @GetMapping(value = "/schema")
    public StoreDefinition getSchema() {
        return new StoreDefinition();
    }

    @PostMapping(value = "")
    public List<Item> getStore(@RequestBody StoreDefinition storeDefinition) {
        return generateStore.generateStore(storeDefinition);
    }
}
