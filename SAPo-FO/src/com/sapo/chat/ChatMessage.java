package com.sapo.chat;

import java.util.Date;

public class ChatMessage {
	private String message;
	private String sender;
	private Date received;
	private String avatar;

	public final String getMessage() {
		return message;
	}

	public final void setMessage(final String message) {
		this.message = message;
	}

	public final String getSender() {
		return sender;
	}

	public final void setSender(final String sender) {
		this.sender = sender;
	}

	public final Date getReceived() {
		return received;
	}

	public final void setReceived(final Date received) {
		this.received = received;
	}
	
	public final String getAvatar() {
		return avatar;
	}

	public final void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	@Override
	public String toString() {
		return "ChatMessage [message=" + message + ", sender=" + sender + ", avatar=" + avatar + "]";
	}


}
