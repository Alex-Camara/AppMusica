 
import threading
import socket
import sys
import os
from pydub import AudioSegment

class ClientThread(threading.Thread):
    def __init__(self,clientAddress,clientsocket):
        threading.Thread.__init__(self)
        self.csocket = clientsocket
        print ("Nueva conexion ", clientAddress)
    def run(self):
        print ("Connection from : ", clientAddress)
        #self.csocket.send(bytes("Hi, This is from Server..",'utf-8'))
        msg = ''
        #while True:
        """data = self.csocket.recv(2048)
        msg = data.decode()
        if msg=='bye':
          break
        print ("from client", msg)
        self.csocket.send(bytes(msg,'UTF-8'))"""

        print("directorio actual: " + os.getcwd())

        data = self.csocket.recv(1024)

        directorio = data.rstrip()
        directorio = os.getcwd() + "/LibreriaMusica/" + directorio
        data = ""
        print("directorio: " + directorio)

        if not os.path.exists(directorio):
            print("no existe el directorio")
            os.makedirs(directorio)

        data = self.csocket.recv(1024)
        nombreArchivo = data.rstrip()
        data = ""
        print("archivo: " + nombreArchivo)

        #Receive, output and save file
        archivo = directorio + nombreArchivo
        print("archivo completo: " + archivo)

        with open(archivo, "wb") as fw:
            print("Recibiendo cancion ..")
            while True:
                print('Recibiendo...')
                data = self.csocket.recv(1024)
                print('Descarga completa! ', data)
                if not data:
                    print('Breaking from file write')
                    break
                fw.write(data)
                print('Se escribio el archivo: ', data)
            fw.close()
            print("Cancion recibida..")

            sound = AudioSegment.from_file(archivo)
            print("conviertiendo archivos...")
            print("preparando archivo de 48k...")
            sound.export(directorio + "/0.mp3", format="mp3", bitrate="48k")
            print("preparando archivo de 256k...")
            sound.export(directorio + "/1.mp3", format="mp3", bitrate="256k")
            print("preparando archivo de 320k...")
            sound.export(directorio + "/2.mp3", format="mp3", bitrate="320k")

            print("removiendo archivo original: " + archivo)
            os.remove(archivo)
        print ("Client at ", clientAddress , " disconnected...")
LOCALHOST = ''
PORT = 1236
server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
server.bind((LOCALHOST, PORT))
print("Server started")
print("Waiting for client request..")
while True:
    server.listen(1)
    clientsock, clientAddress = server.accept()
    newthread = ClientThread(clientAddress, clientsock)
    newthread.start()
