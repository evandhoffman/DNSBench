package com.evanhoffman.dnsbench;

import java.util.List;

public class DNSTester {

	public static void main(String args[]) {
		String resolverIP = args[0];
		String hostname = args[1];
		Integer iterations = Integer.parseInt(args[2]);
		if (iterations < 1) {
			iterations = 1;
		}
		List<String> results = null;
		long startTime = System.nanoTime();
		for (int i = 0; i < iterations; i++) {
			results = DNSRecord.lookup(resolverIP, hostname, DNSRecord.RECORD_A);
		}
		long endTime = System.nanoTime();
		
		long elapsedNs = endTime - startTime;
		
		double elapsedMilliseconds = elapsedNs / (1000.0 * 1000.0);
		double averageQueryMs = elapsedMilliseconds / iterations;
		
		if (results.size() > 0) {
			System.out.println("Resolved "+hostname+" to "+results.get(0)+" against NS "+resolverIP);
		}
		
		System.out.println("Performed "+iterations+" lookups in "+elapsedMilliseconds+" milliseconds.  " +
				"Average "+averageQueryMs+"ms per lookup.");
		
	}
}
