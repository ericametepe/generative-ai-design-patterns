package com.example.agentic_ai;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryManager {

    private final ChatClient chatClient;

    public InventoryManager(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

   final static  String  SYST_PROMPT = """
            You are an inventory manager who orders just in time.
              2. We send you a list of item in json format with the given schema :
              
             {
               "type": "object",
               "properties": {
                 "name": {
                   "type": "string"
                 },
                 "quantity_on_hand": {
                   "type": "integer",
                   "minimum": 0
                 },
                 "weekly_quantity_sold_past_n_weeks": {
                   "type": "array",
                   "items": {
                     "type": "integer",
                     "minimum": 0
                   }
                 },
                 "weeks_to_deliver": {
                   "type": "integer",
                   "minimum": 0
                 }
               },
               "required": [
                 "name",
                 "quantity_on_hand",
                 "weekly_quantity_sold_past_n_weeks",
                 "weeks_to_deliver"
               ]
             } 
              
              3. Identify which of these items need to be reordered this week.
              
              Output in json with the schema of object  Reorder:
              
                          {
                          "type": "object",
                          "properties": {
                            "name": {
                              "type": "string",
                              "description": "the name of the item"
                            },
                            "quantity_to_order": {
                              "type": "integer",
                               "description": "quantity to order",
                               "minimum": 0
                            },
                               "reason_to_reorder": {
                              "type": "string",
                               "description": "reason_to_reorder"
                            }
                        }
                        }
              
            """;
   final static String UserMessage=  """
            --------------- START -------------
            
            {input}
            
            --------------- END -------------
           
            """;

    List<Reorder> reorder(String input){
        return chatClient.prompt()
                .user(s->s.text(UserMessage).param("input",input)).system(SYST_PROMPT)
                .call().responseEntity(new ParameterizedTypeReference<List<Reorder>>() {
                }).getEntity();
    }

}
