import socket
import os
from os.path import expanduser
import threading

class ServidorThread():
    def __init__(self):
        self.host = ''
        self.port = 1235
        self.serverSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.serverSocket.bind((self.host, self.port))    

    def escucha (self):
        self.serverSocket.listen(15)
        while True:
            print "Servidor en espera de clientes"
            cliente, address = self.serverSocket.accept()
            threading.Thread(target = self.recuperarArchivo, args = (cliente, address)).start()
    @staticmethod
    def enviarDatos(archivo, cliente):
        BUFFER_SIZE = 4096
        print "Envia datos al cliente"
        try:
            chunk = archivo.read(BUFFER_SIZE)
            while chunk != "":
                cliente.send(chunk)
                chunk = archivo.read(BUFFER_SIZE)
        finally:
            cliente.close()
            archivo.close()
        return

    def recuperarArchivo(self, cliente, address):
        BUFFER_SIZE = 4096
        #home = expanduser("~")
        home = os.getcwd() + "/LibreriaMusica/"

        ruta = cliente.recv(BUFFER_SIZE)
        pathArchivo = home + ruta
        print pathArchivo
        if os.path.isfile(pathArchivo):
            print "Recupero archivo"
            archivo = open(pathArchivo, "rb")

            self.enviarDatos(archivo, cliente)  
                    
if __name__ == '__main__':  
    ServidorThread().escucha()