package com.shopme.setting.state;

import com.shopme.common.entity.State;
import com.shopme.common.entity.StateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/states")
public class StateRestController {
    private final StateService service;

    @GetMapping("/list")
    public List<StateDTO> listByCountry(@RequestParam("countryId") Long countryId) {
        List<State> statesByCountry = service.findAllByCountryId(countryId);
        List<StateDTO> stateDTOS = new ArrayList<>();
        for (State state : statesByCountry) {
            stateDTOS.add(new StateDTO(state));
        }
        return stateDTOS;
    }
}
