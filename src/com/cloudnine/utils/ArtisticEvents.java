package com.cloudnine.utils;

import com.bugsense.trace.BugSenseHandler;

public class ArtisticEvents {
	
	public static void sendEvent(String eventName) {
		BugSenseHandler.sendEvent(eventName);
	}
	
	
}
