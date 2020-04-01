package com.ucm.dasi.catan.request.trade;

import com.ucm.dasi.catan.request.IRequest;
import java.util.UUID;

public interface ITradeConfirmationRequest extends IRequest {

  UUID getAgreementId();
}
