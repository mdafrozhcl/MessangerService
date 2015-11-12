package org.afroz.webservice.messanger.database;

import java.util.HashMap;
import java.util.Map;
import org.afroz.webservice.messanger.model.Message;
import org.afroz.webservice.messanger.model.Profile;

public class DatabaseClass {
	public static Map<Long, Message> messages = new HashMap<Long, Message>();
	public static Map<String, Profile> profiles = new HashMap<String, Profile>();
	
	public static Map<Long, Message> getMessages(){
		return messages;
	}
	
	public static Map<String, Profile> getProfiles(){
		return profiles;
	}
	

}
