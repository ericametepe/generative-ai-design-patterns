package com.example.agentic_ai;

public record InventoryItem(
     String name,
     int quantityOnHand,
     int[]  weekly_quantity_sold_past_n_weeks,
     int weeks_to_deliver){};

