import RPi.GPIO as GPIO
import time

idServo=35
idLed=40
luz = False
rotador_servo=0

def iniciar_actuadores():
    print ("inicializando actuadores...")
    GPIO.setmode(GPIO.BOARD)
    GPIO.setup(idServo,GPIO.out)
    GPIO.setup(idLed, GPIO.out)
    global rotador_servo
    rotador_servo= GPIO.PWM(idServo, 50)
    rotador_servo.start(7.5)

def abrir_agua():
    print("sirviendo agua")
    rotador_servo.ChangeDutyCycle(4.5)
    time.sleep(1)
    rotador_servo.ChangeDutyCycle(10.5)

def abrir_comida():
    print("sirviendo comida")
    rotador_servo.ChangeDutyCycle(7.5)
    time.sleep(1)
    rotador_servo.ChangeDutyCycle(10.5)

def encender_luz():
    print("luz encendida")
    GPIO.output(idLed,GPIO.HIGH)
    global luz
    luz=True

def apagar_luz():
    print ("luz apagada")
    GPIO.output(idLed, GPIO.LOW)
    global luz
    luz=False

def estado_luz():
    return luz

def finalizar_actuadores():
    print("finalizar actuadores")
    global rotador_servo
    rotador_servo.stop()
    GPIO.cleanup(idLed)
    GPIO.cleanup(idServo)

