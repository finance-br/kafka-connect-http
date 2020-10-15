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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JacksonBodyDecoderTest {

  private JacksonBodyDecoder jacksonBodyDecoder = new JacksonBodyDecoder();

  @Test
  void whenInputIsTest_thenTest() {
    byte[] input = "test".getBytes();
    assertThat(jacksonBodyDecoder.parse(input)).isEqualTo(input);
  }

  @Test
  void whenInputIsTest1_thenTest1() {
    byte[] input = "test1".getBytes();
    assertThat(jacksonBodyDecoder.parse(input)).isEqualTo(input);
  }

}
