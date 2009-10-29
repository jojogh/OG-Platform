/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.transport;

import org.fudgemsg.FudgeContext;
import org.fudgemsg.FudgeMsg;

import com.opengamma.util.ArgumentChecker;

/**
 * 
 *
 * @author kirk
 */
public class ByteArrayFudgeMessageSender implements FudgeMessageSender {
  private final ByteArrayMessageSender _underlying;
  private final FudgeContext _fudgeContext;
  
  public ByteArrayFudgeMessageSender(ByteArrayMessageSender underlying) {
    this(underlying, new FudgeContext());
  }
  
  public ByteArrayFudgeMessageSender(ByteArrayMessageSender underlying, FudgeContext fudgeContext) {
    ArgumentChecker.checkNotNull(underlying, "Underlying ByteArrayMessageSender");
    ArgumentChecker.checkNotNull(fudgeContext, "Fudge Context");
    _underlying = underlying;
    _fudgeContext = fudgeContext;
  }

  /**
   * @return the underlying
   */
  public ByteArrayMessageSender getUnderlying() {
    return _underlying;
  }

  /**
   * @return the fudgeContext
   */
  public FudgeContext getFudgeContext() {
    return _fudgeContext;
  }

  @Override
  public void send(FudgeMsg message) {
    byte[] bytes = getFudgeContext().toByteArray(message);
    getUnderlying().send(bytes);
  }

}
