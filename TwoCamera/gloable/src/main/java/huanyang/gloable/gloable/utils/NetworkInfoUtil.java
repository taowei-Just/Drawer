package huanyang.gloable.gloable.utils;

import android.content.Context;
import android.net.wifi.WifiManager;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Administrator on 2017-11-01.
 */

public class NetworkInfoUtil {
 

    public static  String getNetworkMac(Context context) {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        String macAddress = wifiManager.getConnectionInfo().getMacAddress();

        LogUtil.e("", " NetWorkName   " + wifiManager.getConnectionInfo().getSSID() + " WIFI Mac  " + macAddress);


        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            if (networkInterfaces != null) {
                while (networkInterfaces.hasMoreElements()) {

                    NetworkInterface networkInterface = networkInterfaces.nextElement();

                    if (networkInterface != null) {

                        byte[] hardwareAddress = networkInterface.getHardwareAddress();

                        StringBuffer buffer = new StringBuffer();
                        if (hardwareAddress != null)
                            for (int i = 0; i < hardwareAddress.length; i++) {
                                if (i != 0) {
                                    buffer.append('_');
                                }

                                String str = Integer.toHexString(hardwareAddress[i] & 0xFF);
                                buffer.append(str.length() == 1 ? 0 + str : str);
                            }
                        String strMacAddr = buffer.toString().toUpperCase();

                        LogUtil.e("", " NetWorkName   " + networkInterface.getName() + " strMacAddr " + strMacAddr);

                        if (!strMacAddr.isEmpty()) {

                            return strMacAddr;
                        }

                    }


                }
            }


        } catch (SocketException e) {
            e.printStackTrace();
        } 


        return null;
    }

}
