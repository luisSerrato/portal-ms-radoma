/*
 * Copyright (C) 2018 by Citigroup. All rights reserved. Citigroup claims copyright in this computer
 * program as an unpublished work, one or more versions of which were first used to provide services
 * to customers on the dates indicated in the foregoing notice. Claim of copyright does not imply
 * waiver of other rights.
 *
 * NOTICE OF PROPRIETARY RIGHTS
 *
 * This program is a confidential trade secret and the property of Citigroup. Use, examination,
 * reproduction, disassembly, decompiling, transfer and/or disclosure to others of all or any part
 * of this software program are strictly prohibited except by express written agreement with
 * Citigroup.
 */

package com.citibanamex.api.productname.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Getter
public class ProductNameProperties {


  @Value("${labelcatlogs.message.resource.not.found.description}")
  private String messageResourceNotFoundDescription;

  @Value("${labelcatlogs.message.resource.not.found}")
  private String messageResourceNotFound;

  @Value("${labelcatlogs.message.error}")
  private String messageError;

  @Value("${labelcatlogs.message.http.reponse}")
  private String messageHttpResponse;

  @Value("${labelcatlogs.hyphen}")
  private String messageHyphen;

  @Value("${labelcatlogs.message.missing.request.parameter}")
  private String messageMissingRequestParameter;

  @Value("${labelcatlogs.message.missing.parameter}")
  private String messageMissingParameter;

  @Value("${labelcatlogs.message.missing.parameter.type}")
  private String messageMissingParameterType;

  @Value("${labelcatlogs.message.authoriztion}")
  private String messageAuthorization;

  @Value("${labelcatlogs.message.unauthorized.description}")
  private String messageUnauthorizedDescription;

  @Value("${labelcatlogs.message.uuid}")
  private String messageUuid;

  @Value("${labelcatlogs.message.uuid.description}")
  private String messageUuidDescription;

  @Value("${labelcatlogs.message.accept}")
  private String messageAccept;

  @Value("${labelcatlogs.message.accept.description}")
  private String messageAcceptDescription;

  @Value("${labelcatlogs.message.accept.language}")
  private String messageAcceptLanguage;

  @Value("${labelcatlogs.message.accept.language.description}")
  private String messageAcceptLanguageDescription;

  @Value("${labelcatlogs.message.client.id}")
  private String messageClientId;

  @Value("${labelcatlogs.message.client.id.description}")
  private String messageClientIdDescription;

  @Value("${labelcatlogs.message.response.error}")
  private String responseMessageError;

  @Value("${labelcatlogs.message.empty}")
  private String messageEmptyString;

  @Value("${labelcatlogs.message.bin.empty}")
  private String messageBinEmpty;

  @Value("${labelcatlogs.message.bin.numeric}")
  private String messageBinNumeric;

  @Value("${labelcatlogs.message.bin.length}")
  private String messageBinLength;

  @Value("${labelcatlogs.message.bin.productinfo.catalog}")
  private String productinfoCatalog ;

  @Value("${labelcatlogs.message.internal.server.error}")
  private String messageInternalServerError;

  @Value("${labelcatlogs.message.internal.server.error.description}")
  private String messageInternalServerErrorDescription;

  @Value("${labelcatlogs.message.external.server.error.description}")
  private String messageExternalServerErrorDescription;

  @Value("${labelcatlogs.message.invalid.bin}")
  private String messageInvalidBin;

  @Value("${labelcatlogs.message.externalapi.server.name}")
  private String externalApiServername;

  @Value("${labelcatlogs.message.externalapi.url}")
  private String externalApiUrl;

  @Value("${labelcatlogs.message.externalapi.http.prefix}")
  private String externalHttpPrefix;

  @Value("${circuit.breaker.external.service.timeout}")
  private String timeout;

  @Value("${labelcatlogs.message.service.down}")
  private String responseFallbackError;

  @Value("${labelcatlogs.message.service.down.description}")
  private String responseFallbackErrorDescription;

  private ProductNameProperties() {}


}
