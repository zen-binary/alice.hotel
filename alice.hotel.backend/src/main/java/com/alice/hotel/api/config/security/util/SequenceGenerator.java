package com.alice.hotel.api.config.security.util;

import com.alice.hotel.api.repository.system.SequenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SequenceGenerator {

    @Autowired
    private SequenceRepository sequenceRepository;

    public String getNextSequence(String prefix, String tableName) {
        return sequenceRepository.getSequence(prefix, tableName);
    }
}
