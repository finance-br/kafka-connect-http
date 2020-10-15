package com.github.castorm.kafka.connect.http.model;

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

import lombok.Builder;

@Builder
public class B3 {
  private int TIPREG;
  private String DATPRE;
  private String CODBDI;
  private String CODNEG;
  private int TPMERC;
  private String NOMRES;
  private String ESPECI;
  private String PRAZOT;
  private String MODREF;
  private Double PREABE;
  private Double PREMAX;
  private Double PREMIN;
  private Double PREMED;
  private Double PREULT;
  private Double PREOFC;
  private Double PREOFV;
  private int TOTNEG;
  private int QUATOT;
  private Double VOLTOT;
  private Double PREEXE;
  private int INDOPC;
  private String DATVEN;
  private int FATCOT;
  private String PTOEXE;
  private String CODISI;
  private String DISMES;
}
