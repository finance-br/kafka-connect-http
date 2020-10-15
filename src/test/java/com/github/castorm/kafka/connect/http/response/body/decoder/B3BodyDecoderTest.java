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

class B3BodyDecoderTest {

  private B3BodyDecoder b3BodyDecoder = new B3BodyDecoder();

  private byte[] zipBody = Base64.getDecoder().decode("UEsDBBQAAgAIAFS9TlECXQ8jjgAAAOUCAAAWABwAQ09UQUhJU1RfRDA4MTAyMDIwLlRYVFVUCQADz3CHX89wh191eAsAAQT2AQAABBQAAADdkUkOwyAMRfeRcgcWXVffDAGWECKVRUJFot7/KCUKnaMeIH9l+3mQ9YE+Le4S5+XMweHTbZivjq0xAYYdUm0DenwI7siPQm6klJyPaRwCi1PPQp5eU/lUeJXQnaFnUpZgn2gt5G7bvn64VW+JUEZq891RRFzUk/TBfF5/8yEDXSFtY+1ft+uYkkYdyu07UEsBAh4DFAACAAgAVL1OUQJdDyOOAAAA5QIAABYAGAAAAAAAAQAAALSBAAAAAENPVEFISVNUX0QwODEwMjAyMC5UWFRVVAUAA89wh191eAsAAQT2AQAABBQAAABQSwUGAAAAAAEAAQBcAAAA3gAAAAAA");
  private byte[] jsonAsBytes = {91, 123, 34, 84, 73, 80, 82, 69, 71, 34, 58, 49, 44, 34, 68, 65, 84, 80, 82, 69, 34, 58, 34, 50, 48, 50, 48, 49, 48, 48, 56, 34, 44, 34, 67, 79, 68, 66, 68, 73, 34, 58, 34, 48, 50, 34, 44, 34, 67, 79, 68, 78, 69, 71, 34, 58, 34, 65, 49, 66, 77, 51, 52, 34, 44, 34, 84, 80, 77, 69, 82, 67, 34, 58, 49, 48, 44, 34, 78, 79, 77, 82, 69, 83, 34, 58, 34, 65, 66, 73, 79, 77, 69, 68, 32, 73, 78, 67, 34, 44, 34, 69, 83, 80, 69, 67, 73, 34, 58, 34, 68, 82, 78, 34, 44, 34, 80, 82, 65, 90, 79, 84, 34, 58, 34, 34, 44, 34, 77, 79, 68, 82, 69, 70, 34, 58, 34, 82, 36, 34, 44, 34, 80, 82, 69, 65, 66, 69, 34, 58, 49, 56, 56, 52, 46, 48, 53, 44, 34, 80, 82, 69, 77, 65, 88, 34, 58, 49, 57, 48, 49, 46, 48, 44, 34, 80, 82, 69, 77, 73, 78, 34, 58, 49, 56, 56, 52, 46, 48, 53, 44, 34, 80, 82, 69, 77, 69, 68, 34, 58, 49, 56, 56, 54, 46, 55, 44, 34, 80, 82, 69, 85, 76, 84, 34, 58, 49, 57, 48, 49, 46, 48, 44, 34, 80, 82, 69, 79, 70, 67, 34, 58, 48, 46, 48, 44, 34, 80, 82, 69, 79, 70, 86, 34, 58, 48, 46, 48, 44, 34, 84, 79, 84, 78, 69, 71, 34, 58, 50, 44, 34, 81, 85, 65, 84, 79, 84, 34, 58, 57, 53, 44, 34, 86, 79, 76, 84, 79, 84, 34, 58, 49, 55, 57, 50, 51, 57, 46, 48, 44, 34, 80, 82, 69, 69, 88, 69, 34, 58, 48, 46, 48, 44, 34, 73, 78, 68, 79, 80, 67, 34, 58, 48, 44, 34, 68, 65, 84, 86, 69, 78, 34, 58, 34, 57, 57, 57, 57, 49, 50, 51, 49, 34, 44, 34, 70, 65, 84, 67, 79, 84, 34, 58, 49, 44, 34, 80, 84, 79, 69, 88, 69, 34, 58, 34, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 48, 34, 44, 34, 67, 79, 68, 73, 83, 73, 34, 58, 34, 66, 82, 65, 49, 66, 77, 66, 68, 82, 48, 48, 54, 34, 44, 34, 68, 73, 83, 77, 69, 83, 34, 58, 34, 49, 48, 48, 34, 125, 93};


  @Test
  void whenReceiveAValidB3FileZipped_thenReturnJsonParsedAsByteArray() {
    assertThat(b3BodyDecoder.parse(zipBody)).isEqualTo(jsonAsBytes);
  }
}
