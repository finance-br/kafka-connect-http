package com.github.castorm.kafka.connect.http.response.body.decoder;

/*-
 * #%L
 * Kafka Connect HTTP
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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.castorm.kafka.connect.http.model.B3;
import com.github.castorm.kafka.connect.http.response.spi.BodyDecoder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class B3BodyDecoder implements BodyDecoder {

  private ZipBodyDecoder zipBodyDecoder = new ZipBodyDecoder();
  private ObjectMapper objectMapper = new ObjectMapper()
          .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);;

  @SneakyThrows
  @Override
  public byte[] parse(byte[] body) {
    List<String> b3Rows = Arrays.asList(
            new String(zipBodyDecoder.parse(body), "cp1252").split("\\r?\\n")
    );

    List<B3> b3List = b3Rows.subList(1, b3Rows.size() - 1).stream().map(row ->
            mapStringToB3(row)
    ).collect(Collectors.toList());

    return objectMapper.writeValueAsString(b3List).getBytes();
  }

  private B3 mapStringToB3(String text) {
    return B3.builder()
            .TIPREG(mapStringToInt(text, 0, 2, false))
            .DATPRE(mapString(text, 2, 10))
            .CODBDI(mapString(text, 10, 12))
            .CODNEG(mapString(text, 12, 24))
            .TPMERC(mapStringToInt(text, 24, 27, false))
            .NOMRES(mapString(text, 27, 39))
            .ESPECI(mapString(text, 39, 49))
            .PRAZOT(mapString(text, 49, 52))
            .MODREF(mapString(text, 52, 56))
            .PREABE(mapStringToFloat(text, 56, 69, true, 2))
            .PREMAX(mapStringToFloat(text, 69, 82, true, 2))
            .PREMIN(mapStringToFloat(text, 82, 95, true, 2))
            .PREMED(mapStringToFloat(text, 95, 108, true, 2))
            .PREULT(mapStringToFloat(text, 108, 121, true, 2))
            .PREOFC(mapStringToFloat(text, 121, 134, true, 2))
            .PREOFV(mapStringToFloat(text, 134, 147, true, 2))
            .TOTNEG(mapStringToInt(text, 147, 152, true))
            .QUATOT(mapStringToInt(text, 152, 170, true))
            .VOLTOT(mapStringToFloat(text, 170, 188, true, 2))
            .PREEXE(mapStringToFloat(text, 188, 201, true, 2))
            .INDOPC(mapStringToInt(text, 201, 202, true))
            .DATVEN(mapString(text, 202, 210))
            .FATCOT(mapStringToInt(text, 210, 217, true))
            .PTOEXE(mapString(text, 217, 230))
            .CODISI(mapString(text, 230, 242))
            .DISMES(mapString(text, 242, 245))
            .build();
  }

  private int mapStringToInt(String text, int start, int end, boolean replaceSpacesEnabled) {
    return Integer.parseInt(replaceSpaces(text.substring(start, end), replaceSpacesEnabled));
  }

  private String replaceSpaces(String text, boolean replaceSpacesEnabled) {
    if (replaceSpacesEnabled) {
      return text.replace(' ', '0');
    } else {
      return text;
    }
  }

  private double mapStringToFloat(String text, int start, int end, boolean replaceSpacesEnabled,
                                  int decimalPoint) {
    return Double.parseDouble(replaceSpaces(text.substring(start, end), replaceSpacesEnabled))
            / getDivisor(decimalPoint);
  }

  private double getDivisor(int decimalPoint) {
    double value = 10;
    for (int i = 1; i < decimalPoint; i++) {
      value *= decimalPoint;
    }
    return value;
  }

  private String mapString(String text, int start, int end) {
    return text.substring(start, end).trim();
  }
}
