package com.midtownmadness.bubblecombat.multiplay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public class BluetoothMessage {
	public MessageType messageType;
	public Object payload;

	public BluetoothMessage(MessageType messageType, Object payload) {
		this.messageType = messageType;
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "BluetoothMessage [messageType=" + messageType + ", payload="
				+ payload + "]";
	}

	public byte[] toBytes() {
		ByteArrayOutputStream memoryStream = new ByteArrayOutputStream();
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					memoryStream);
			objectOutputStream.writeInt(messageType.ordinal());
			objectOutputStream.writeObject(payload);
		} catch (IOException e) {
			// should never happen!
			e.printStackTrace();
		}
		return memoryStream.toByteArray();
	}
}
