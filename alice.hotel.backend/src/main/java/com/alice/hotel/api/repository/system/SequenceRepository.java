package com.alice.hotel.api.repository.system;

import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * MySQL-friendly ID generator (Oracle FN_GET_SEQ was removed for Alice demo).
 */
@Repository
public class SequenceRepository {

    public String getSequence(String prefix, String tableName) {
        String safePrefix = (prefix == null || prefix.isBlank()) ? "ID" : prefix.trim();
        return safePrefix + "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }
}
