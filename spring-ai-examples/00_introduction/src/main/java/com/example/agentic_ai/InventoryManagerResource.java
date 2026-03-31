package com.example.agentic_ai;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class InventoryManagerResource {

    final InventoryManager inventoryManager;

    @GetMapping(value = "/reorders")
    public ResponseEntity<List<Reorder>> getOlympe() {
        var obj = new ObjectMapper();
        var items =obj.writeValueAsString(getAll());
        var resp =  inventoryManager.reorder(items);
        return new ResponseEntity<>(resp,OK);
    }

    static List<InventoryItem> getAll(){
        var items = new InventoryItem[]{
                new InventoryItem("itemA", 300, new int[]{50, 70, 80, 100}, 2),
                new InventoryItem("itemB", 100, new int[]{70, 80, 90, 70}, 2),
                new InventoryItem("itemC", 200, new int[]{80, 70, 90, 80}, 1)
        };

        return List.of(items);

    }

}
