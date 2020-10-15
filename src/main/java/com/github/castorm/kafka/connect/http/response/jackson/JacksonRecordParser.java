package com.github.castorm.kafka.connect.http.response.jackson;

/*-
 * #%L
 * kafka-connect-http
 * %%
 * Copyright (C) 2020 CastorM
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.fasterxml.jackson.core.JsonPointer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.castorm.kafka.connect.http.response.spi.BodyDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.common.Configurable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public class JacksonRecordParser implements Configurable {

    private final Function<Map<String, ?>, JacksonRecordParserConfig> configFactory;

    private final ObjectMapper objectMapper;

    private final JacksonPropertyResolver propertyResolver;

    private JsonPointer recordsPointer;
    private List<JsonPointer> keyPointer;
    private Optional<JsonPointer> timestampPointer;
    private Map<String, JsonPointer> offsetPointers;
    private JsonPointer valuePointer;
    private BodyDecoder bodyDecoder;

    public JacksonRecordParser() {
        this(JacksonRecordParserConfig::new, new ObjectMapper(), new JacksonPropertyResolver());
    }

    @Override
    public void configure(Map<String, ?> settings) {
        JacksonRecordParserConfig config = configFactory.apply(settings);
        recordsPointer = config.getRecordsPointer();
        keyPointer = config.getKeyPointer();
        valuePointer = config.getValuePointer();
        offsetPointers = config.getOffsetPointers();
        timestampPointer = config.getTimestampPointer();
        bodyDecoder = config.getBodyDecoder();
    }

    Stream<JsonNode> getRecords(byte[] body) {
        return propertyResolver.getArrayAt(deserialize(bodyDecoder.parse(body)), recordsPointer);
    }

    Optional<String> getKey(JsonNode node) {
        String key = keyPointer.stream()
                .map(pointer -> propertyResolver.getObjectAt(node, pointer).asText())
                .filter(it -> !it.isEmpty())
                .collect(joining("+"));
        return key.isEmpty() ? Optional.empty() : Optional.of(key);
    }

    Optional<String> getTimestamp(JsonNode node) {
        return timestampPointer.map(pointer -> propertyResolver.getObjectAt(node, pointer).asText());
    }

    Map<String, Object> getOffsets(JsonNode node) {
        return offsetPointers.entrySet().stream()
                .collect(toMap(Entry::getKey, entry -> propertyResolver.getObjectAt(node, entry.getValue()).asText()));
    }

    String getValue(JsonNode node) {

        JsonNode value = propertyResolver.getObjectAt(node, valuePointer);

        return value.isObject() ? serialize(value) : value.asText();
    }

    @SneakyThrows(IOException.class)
    private JsonNode deserialize(byte[] body) {
        return objectMapper.readTree(body);
    }

    @SneakyThrows(IOException.class)
    private String serialize(JsonNode node) {
        return objectMapper.writeValueAsString(node);
    }
}
