package com.shopme.admin.setting.state;

import com.shopme.common.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StateDTO {
    private Long id;
    private String name;

    public StateDTO(State state) {
        BeanUtils.copyProperties(state, this);
    }
}
