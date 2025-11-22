package app.adapters.mappers;

import app.adapters.dto.ticket.TicketLuggageArray;
import app.adapters.dto.ticket.TicketWithLuggage;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;

import java.util.*;

public class TicketLuggageMapper {

    public static Map<BaseTicketDTO, List<LuggageDTO>> toMap(TicketLuggageArray array) {
        Map<BaseTicketDTO, List<LuggageDTO>> result = new LinkedHashMap<>();

        if (array == null || array.getItems() == null) return result;

        for (TicketWithLuggage twl : array.getItems()) {
            result.put(twl.getTicket(), twl.getLuggages());
        }

        return result;
    }

    public static TicketLuggageArray fromMap(Map<BaseTicketDTO, List<LuggageDTO>> map) {
        TicketLuggageArray array = new TicketLuggageArray();
        List<TicketWithLuggage> items = new ArrayList<>();

        if (map != null) {
            for (Map.Entry<BaseTicketDTO, List<LuggageDTO>> entry : map.entrySet()) {
                TicketWithLuggage twl = new TicketWithLuggage();
                twl.setTicket(entry.getKey());
                twl.setLuggages(entry.getValue());
                items.add(twl);
            }
        }

        array.setItems(items);
        return array;
    }
}