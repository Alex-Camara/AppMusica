import socket
import os
from os.path import expanduser
#Para hacer que esto funcione debe haber en tu home una carpeta llamada LibreriaMusica y adentro 
#/Yeah Yeah Yeahs/Fever/maps.mp3 (u otra canción, pero deberá cambiarse el nombre del archivo en el cliente)

UDP_HOST = "127.0.0.1"
UDP_PORT = 1234
#Aquí utilizo un buffer de 2200
BUFFER_SIZE = 2200
serverSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
serverSocket.bind((UDP_HOST, UDP_PORT))
print "Servidor en espera de clientes"
def recuperarArchivo(ruta):
    home = expanduser("~")
    home = home + "/LibreriaMusica/"
    pathArchivo = home + ruta
    if os.path.isfile(pathArchivo):
        archivo = open(pathArchivo, "rb")
        print "Recupero archivo"
        return archivo
    return
def enviarDatos(archivo, remitente):
    while True:
        chunk = archivo.read(BUFFER_SIZE)
        if chunk is None:
            archivo.close()
            break
        serverSocket.sendto(chunk, remitente)
        print "Envio un chunk"
        
while True:
    ruta, remitente = serverSocket.recvfrom(BUFFER_SIZE)
    archivo = recuperarArchivo(ruta)
    enviarDatos(archivo, remitente)