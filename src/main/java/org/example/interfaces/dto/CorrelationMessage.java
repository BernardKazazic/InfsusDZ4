package org.example.interfaces.dto;

import java.util.Map;

public record CorrelationMessage(String messageName, String businessKey, Map<String, Variable> processVariables) {
}
