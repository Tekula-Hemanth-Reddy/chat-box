package chatserver;

import java.util.Comparator;

import chatserver.node;

public class sortbyfreq implements Comparator <node>{

	public int compare(node a, node b) {
		
		return a.frequency-b.frequency;
	}

}