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

import com.github.castorm.kafka.connect.http.response.spi.BodyDecoder;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipBodyDecoder implements BodyDecoder {

  @SneakyThrows
  public byte[] parse(byte[] body) {
    if (body.length == 0) {
      throw new RuntimeException("Body is empty");
    }

    ByteArrayInputStream byteStream = new ByteArrayInputStream(body);
    ZipInputStream zipStream = new ZipInputStream(byteStream);
    ZipEntry nextEntry = zipStream.getNextEntry();

    byte[] output = new byte[(int) nextEntry.getSize()];
    zipStream.read(output, 0, output.length);

    return output;
  }
}
