import RPi.GPIO as GPIO

idSensorAgua=40
idSensorComida=38
idSensorLuz=40

def iniciar_sensores():
    print("iniciando sensores")
    # GPIO.setmode(GPIO.BCM)

def hay_agua():
    return True

def hay_comida():
    return True

def estado_luz():
    return GPIO.input(idSensorLuz)

def finalizar_sensores():
    print("finalizar sensores")


