package com.withus.withmebe.payment.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
  CREATED("결제대기"),
  APPROVED("결제승인"),
  CANCELED("결제취소"),
  ;

  private final String value;
}
