import requests

def obtener_mac(interface='eth0'):
    try:
        str = open('/sys/class/net/%s/address' % interface).read()
    except:
        str = "00:00:00:00:00:00"
    return str[0:17]

raspberry=str(obtener_mac())

direccion_base = 'https://tranquil-mountain-87492.herokuapp.com/'

def consultar_orden():
    orden_actual = requests.get(direccion_base + 'Raspberry/obtenerOrden?raspberry=' + raspberry)

    try:
        return (orden_actual.json())
    except:
        return ("sin orden")

def actualizar_senso(senso):
    requests.get(direccion_base
                 +'Raspberry/actualizarSenso?raspberry='
                 + raspberry
                 + '&&estado='
                 + senso)
    print (senso)

def subirArchivo():
    with open('bati.PNG', 'rb') as file:
        r = requests.post(direccion_base
                          +'Raspberry/subirVideo',
                          files={'file': file})



