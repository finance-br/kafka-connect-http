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

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ZipBodyDecoderTest {
  private ZipBodyDecoder zipBodyDecoder = new ZipBodyDecoder();

  private String zipBody = "UEsDBBQAAgAIAFS9TlECXQ8jjgAAAOUCAAAWABwAQ09UQUhJU1RfRDA4MTAyMDIwLlRYVFVUCQADz3CHX89wh191eAsAAQT2AQAABBQAAADdkUkOwyAMRfeRcgcWXVffDAGWECKVRUJFot7/KCUKnaMeIH9l+3mQ9YE+Le4S5+XMweHTbZivjq0xAYYdUm0DenwI7siPQm6klJyPaRwCi1PPQp5eU/lUeJXQnaFnUpZgn2gt5G7bvn64VW+JUEZq891RRFzUk/TBfF5/8yEDXSFtY+1ft+uYkkYdyu07UEsBAh4DFAACAAgAVL1OUQJdDyOOAAAA5QIAABYAGAAAAAAAAQAAALSBAAAAAENPVEFISVNUX0QwODEwMjAyMC5UWFRVVAUAA89wh191eAsAAQT2AQAABBQAAABQSwUGAAAAAAEAAQBcAAAA3gAAAAAA";

  private String textBody = "00COTAHIST.2020BOVESPA 20201008                                                                                                                                                                                                                      \r\n" +
          "012020100802A1BM34      010ABIOMED INC DRN          R$  000000003768100000000380200000000037681000000003773400000000380200000000000000000000000000000002000000000000000095000000000003584780000000000000009999123100000010000000000000BRA1BMBDR006100\r\n" +
          "99COTAHIST.2020BOVESPA 2020100800000005485                                                                                                                                                                                                           \r\n";

  @Test
  void whenBodyIsEmpty_thenThrowRuntimeException() {
    assertThrows(RuntimeException.class, () -> {
      zipBodyDecoder.parse(new byte[0]);
    });
  }

  @Test
  void whenBodyHasAValidZip_thenReturnTextBodyAsByteArray() {
    assertThat(zipBodyDecoder.parse(Base64.getDecoder().decode(zipBody))).isEqualTo(textBody.getBytes());
  }
}
