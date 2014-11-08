package com.midtownmadness.bubblecombat.multiplay;

public enum MessageType {
	JOIN_GAME, GO, ERROR;

	public static MessageType getByOrdinal(int ordinal) {
		for (MessageType type : MessageType.values()) {
			if (type.ordinal() == ordinal) {
				return type;
			}
		}
		return null;
	}
}
