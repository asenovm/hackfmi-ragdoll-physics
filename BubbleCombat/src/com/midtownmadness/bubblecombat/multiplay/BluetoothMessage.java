package com.midtownmadness.bubblecombat.multiplay;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BluetoothMessage {
	public MessageType messageType;
	public Object payload;

	public static BluetoothMessage from(byte[] byteArray) {
		return new BluetoothMessage(byteArray);
	}

	public static byte[] from(MessageType messageType, Object payload) {
		return new BluetoothMessage(messageType, payload).toBytes();
	}

	private BluetoothMessage(MessageType messageType, Object payload) {
		this.messageType = messageType;
		this.payload = payload;
	}

	private BluetoothMessage(byte[] byteArray) {
		Exception exc = null;
		try {
			ObjectInputStream stream = new ObjectInputStream(
					new ByteArrayInputStream(byteArray));
			this.messageType = getByOrdinal(stream.readInt());
			this.payload = stream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			this.messageType = MessageType.ERROR;
			this.payload = null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("This should not be possible!");
		}
	}

	private MessageType getByOrdinal(int ordinal) {
		for (MessageType type : MessageType.values()) {
			if (type.ordinal() == ordinal) {
				return type;
			}
		}
		return null;
	}

	private byte[] toBytes() {
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
