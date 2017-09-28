package framework.transfer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTransfers {
	
	private static Map<String, Transfer> transfers = new HashMap<String, Transfer>();
	
	public static final String addTransfer(String protocol, final Transfer transfer) {
		String newProtocol = protocol;
		for(int i = 1; transfers.get(newProtocol) != null; i++, 
				newProtocol = protocol + "_" + i);
		
		setTransfer(newProtocol, transfer);
		return newProtocol;
	}
	
	public static final Transfer setTransfer(final String protocol, final Transfer transfer) {
		return transfers.put(protocol, transfer);
	}
	
	public static final Transfer getTransfer(final String protocol) {
		return transfers.get(protocol);
	}
	
	public static final Transfer replaceTransfer(final String protocol, final Transfer transfer) {
		return transfers.replace(protocol, transfer);
	}
	
	public static List<Transfer> searchTransfers(String protocol) {
		List<Transfer> result = new ArrayList<Transfer>();
		
		for(Map.Entry<String, Transfer> entry : transfers.entrySet())
			if(entry.getKey().contains(protocol))
				result.add(entry.getValue());
		
		return result;
	}
	
	public static List<String> getProtocols() {
		List<String> result = new ArrayList<String>();
		
		for(Map.Entry<String, Transfer> entry : transfers.entrySet())
			result.add(entry.getKey());
		
		return result;
	}
	
	public static List<Transfer> getTransfers() {
		List<Transfer> result = new ArrayList<Transfer>();
		
		for(Map.Entry<String, Transfer> entry : transfers.entrySet())
			result.add(entry.getValue());
		
		return result;
	}
}
