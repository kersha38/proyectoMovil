def obtener_mac():
    def getMAC(interface='eth0'):
        # Return the MAC address of the specified interface
        try:
            str = open('/sys/class/net/%s/address' % interface).read()
        except:
            str = "00:00:00:00:00:00"
        return str[0:17]
a=obtener_mac()
print (a)