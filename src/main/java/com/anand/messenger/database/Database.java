package com.anand.messenger.database;

import java.util.HashMap;
import java.util.Map;

import com.anand.messenger.model.Message;
import com.anand.messenger.model.Profile;

public class Database {
	
	public static Map<Long, Message> messages = new HashMap<Long, Message>();
	public static Map<String, Profile> profiles = new HashMap<String, Profile>();

	public static Map<Long, Message> getMessages(){
		return messages;
	}
	
	public static Map<String, Profile> getProfiles(){
		return profiles;
	}
}


