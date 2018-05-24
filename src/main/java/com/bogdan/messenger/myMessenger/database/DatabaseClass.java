package com.bogdan.messenger.myMessenger.database;

import java.util.HashMap;
import java.util.Map;

import com.bogdan.messenger.myMessenger.model.Message;
import com.bogdan.messenger.myMessenger.model.Profile;

public class DatabaseClass {

	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<String, Profile> profiles = new HashMap<>();

	//te intrebi de ce nu am pus in constructor?
	//Jersey creeaza o noua instanta a lui MessageResource la fiecare request
	// deci practic daca as face un update, apoi un GET, se suprascrie
	static {
		messages.put(1L, new Message(1, "Hello World", "koushik"));
		messages.put(2L, new Message(2, "Hello Jersey", "koushik"));
		profiles.put("koushik", new Profile(1L, "koushik", "Koushik", "Kothagal"));
	}
	
	public static Map<Long, Message> getMessages() {
		return messages;
	}
	
	public static Map<String, Profile> getProfiles() {
		return profiles;
	}

	
	
	
}
