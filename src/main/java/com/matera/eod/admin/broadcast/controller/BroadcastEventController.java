package com.matera.eod.admin.broadcast.controller;


import com.matera.eod.admin.broadcast.dto.BroadcastEventDto;
import com.matera.eod.admin.broadcast.service.BroadcastEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para envio de mensagens de broadcast.
 */
@RestController
@RequestMapping("/broadcast")
@RequiredArgsConstructor
public class BroadcastEventController {

    private final BroadcastEventService service;

    @PostMapping("/send")
    public String sendBroadcastEvent(@RequestBody BroadcastEventDto dto) {
        dto.setEventTime(System.currentTimeMillis()); // Atualiza o timestamp
        service.saveBroadcastEvent(dto); // Persiste no banco
        return "Broadcast event saved.";
    }
}
