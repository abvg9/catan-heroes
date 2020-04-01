package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.player.IPlayer;
import com.ucm.dasi.catan.request.Request;
import com.ucm.dasi.catan.request.RequestType;
import java.util.UUID;

public class TradeConfirmationRequest extends Request implements ITradeConfirmationRequest {

  private UUID agreementId;

  public TradeConfirmationRequest(IPlayer player, RequestType type, UUID agreementId) {
    super(player, type);

    this.agreementId = agreementId;
  }

  @Override
  public UUID getAgreementId() {
    return agreementId;
  }
}
