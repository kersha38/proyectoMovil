import threading
import requests
#import time

hilos = []
direccionBase = 'https://tranquil-mountain-87492.herokuapp.com/'#"http://localhost:3000/"

def empezarHilos(arrHilos):
    for hilo in arrHilos:
        hilo.start()


def realizarOrden(orden):
    print "hago algo" + str(orden)
    if orden == "agua":
        print "abrir agua"
    elif orden == "comida":
        print "abrir comida"
    elif orden == "luzON":
        print "prender luz"
    elif orden == "luzOFF":
        print "apagar luz"


def consultarOrden(raspberry):
    while True:
        ordenActual = requests.get(direccionBase+'Raspberry/obtenerOrden?raspberry=' + raspberry)

        try:
            realizarOrden(ordenActual.json())
        # time.sleep(1)
        except:
            print "sin orden"


def actualizarSenso(raspberry):
    while True:
        senso = "sensado"
        requests.get(direccionBase+'Raspberry/actualizarSenso?raspberry=' + raspberry + '&&estado=' + senso)
        print senso


hiloConsultaOrden = threading.Thread(target=consultarOrden, args=(str(1),))
hilos.append(hiloConsultaOrden)
hiloSensar = threading.Thread(target=actualizarSenso, args=(str(1),))
hilos.append(hiloSensar)
empezarHilos(hilos)
