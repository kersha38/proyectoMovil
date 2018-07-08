import threading
import requests

hilos = []
direccion_base = 'https://tranquil-mountain-87492.herokuapp.com/'

def obtener_mac():
    def getMAC(interface='eth0'):
        # Return the MAC address of the specified interface
        try:
            str = open('/sys/class/net/%s/address' % interface).read()
        except:
            str = "00:00:00:00:00:00"
        return str[0:17]

def empezar_hilos(arr_hilos):
    for hilo in arr_hilos:
        hilo.start()


def realizar_orden(orden):
    print ("hago algo" + str(orden))
    if orden == "agua":
        print ("abrir agua")
    elif orden == "comida":
        print ("abrir comida")
    elif orden == "luzON":
        print ("prender luz")
    elif orden == "luzOFF":
        print ("apagar luz")


def consultar_orden(raspberry):
    while True:
        orden_actual = requests.get(direccion_base+'Raspberry/obtenerOrden?raspberry=' + raspberry)

        try:
            realizar_orden(orden_actual.json())
        except:
            print ("sin orden")


def actualizar_senso(raspberry):
    while True:
        senso = "sensado"
        requests.get(direccion_base+'Raspberry/actualizarSenso?raspberry=' + raspberry + '&&estado=' + senso)
        print (senso)

raspberry_mac= obtener_mac()
hilo_consulta_orden = threading.Thread(target=consultar_orden, args=(str(raspberry_mac),))
hilos.append(hilo_consulta_orden)
hiloSensar = threading.Thread(target=actualizar_senso, args=(str(raspberry_mac),))
hilos.append(hiloSensar)
empezar_hilos(hilos)
