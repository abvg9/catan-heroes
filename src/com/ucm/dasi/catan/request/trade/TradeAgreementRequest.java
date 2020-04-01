package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.Request;
import com.ucm.dasi.catan.request.RequestType;
import com.ucm.dasi.catan.resource.IResourceStorage;
import com.ucm.dasi.catan.resource.ResourceStorage;
import java.util.UUID;

public class TradeAgreementRequest extends Request implements ITradeAgreementRequest {

  private IResourceStorage exchange;

  private UUID id;

  private UUID requestId;

  public TradeAgreementRequest(
      IPlayer player, RequestType type, IResourceStorage exchange, UUID id, UUID requestId) {
    super(player, type);

    this.exchange = new ResourceStorage(exchange);
    this.id = id;
    this.requestId = requestId;
  }

  @Override
  public IResourceStorage getExchange() {
    return exchange;
  }

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public UUID getRequestId() {
    return requestId;
  }
}
