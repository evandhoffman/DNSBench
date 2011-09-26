package com.evanhoffman.dnsbench;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/**
 * Stolen from http://mowyourlawn.com/blog/?p=8
 * @author evan
 *
 */
public class DNSRecord {

    public static final String RECORD_SOA = "SOA";
    public static final String RECORD_A = "A";
    public static final String RECORD_NS = "NS";
    public static final String RECORD_MX = "MX";
    public static final String RECORD_CNAME = "CNAME";

    public static List<String> lookup(String resolverIP, String hostName, String record) {

        List<String> result = new LinkedList<String>();
        try {
            Hashtable<String,String> env = new Hashtable<String,String>();
            env.put(Context.PROVIDER_URL, "dns://"+resolverIP);
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ictx = new InitialDirContext(env);
            Attributes attrs = ictx.getAttributes(hostName, new String[] { record });
            Attribute attr = attrs.get(record);

            NamingEnumeration<?> attrEnum = attr.getAll();
            while (attrEnum.hasMoreElements())
                result.add((String)attrEnum.next());
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return result;
    }
}