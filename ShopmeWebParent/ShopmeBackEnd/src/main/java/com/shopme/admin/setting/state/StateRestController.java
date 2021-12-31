package com.shopme.admin.setting.state;

import com.shopme.common.entity.State;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    public State save(@RequestBody State state) {
        return service.save(state);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
