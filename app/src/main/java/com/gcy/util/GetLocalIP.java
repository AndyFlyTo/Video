package com.gcy.util;

import android.util.Log;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Mr.G on 2016/6/1.
 */
public class GetLocalIP {
    public static String getLocalIpAddress()
    {
        StringBuffer sb = new StringBuffer();
        try {

            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
            {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
                {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    sb.append("_"+inetAddress.getHostAddress());
                }
            }
        }
        catch (SocketException ex) {
            return null;
        }
        String []str = sb.toString().split("_");
        Log.d("chen",str[str.length-1]);
        return str[str.length-1];
    }
}
