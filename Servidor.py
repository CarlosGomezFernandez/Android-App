#!/usr/bin/python3
# -- coding: utf -8; mode:python --
#Alumno: Carlos Gomez Fernandez

import socket

serverAddress = ('192.168.56.1',10000)
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind(serverAddress)

while True:
    mensaje = sock.recvfrom(4096)
    lectura = mensaje[0]
    direccion = mensaje[1]
    mensajeServicio = "Lectura del sensor: {}".format(lectura)
    direccionServicio = "Direccion del servicio: {}".format(direccion)
    print (mensajeServicio)