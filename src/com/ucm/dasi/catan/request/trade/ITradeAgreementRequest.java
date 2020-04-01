package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.request.IRequest;
import com.ucm.dasi.catan.resource.IResourceStorage;
import java.util.UUID;

public interface ITradeAgreementRequest extends IRequest {

  IResourceStorage getExchange();

  UUID getId();

  UUID getRequestId();
}
