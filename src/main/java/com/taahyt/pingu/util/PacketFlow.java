package com.taahyt.pingu.util;

public enum PacketFlow {
   SERVERBOUND,
   CLIENTBOUND;

   public PacketFlow getOpposite() {
      return this == CLIENTBOUND ? SERVERBOUND : CLIENTBOUND;
   }
}