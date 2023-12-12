/*
 * Copyright 2023 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.redhat.exhort.integration.trustedcontent;

import java.io.IOException;
import java.util.*;

import org.apache.camel.Body;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.exhort.config.ObjectMapperProducer;
import com.redhat.exhort.integration.providers.ProviderResponseHandler;
import com.redhat.exhort.model.DependencyTree;
import com.redhat.exhort.model.ProviderResponse;
import com.redhat.exhort.model.trustedcontent.TrustedContentResponse;

import io.quarkus.runtime.annotations.RegisterForReflection;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RegisterForReflection
public class TcResponseHandler extends ProviderResponseHandler {
  public static void main(String[] args) {
    try {
      TrustedContentResponse trustedContentResponse =
          new TcResponseHandler().responseToMap("{ \"recommendations\": {}\n}".getBytes());
      System.out.println(trustedContentResponse);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  ObjectMapper mapper =
      ObjectMapperProducer.newInstance().configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true);

  public TrustedContentResponse responseToMap(@Body byte[] tcResponse) throws IOException {
    TrustedContentResponse trustedContentResponse =
        mapper.readValue(tcResponse, TrustedContentResponse.class);
    return trustedContentResponse;
  }

  @Override
  protected String getProviderName() {
    return "TrustedContent";
  }

  @Override
  public ProviderResponse responseToIssues(
      byte[] response, String privateProviders, DependencyTree tree) throws IOException {
    throw new IllegalArgumentException("not implemented yet");
  }
}
