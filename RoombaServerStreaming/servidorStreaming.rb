require 'socket'
require 'Pathname'
#Para hacer que esto funcione debe haber en tu home una carpeta llamada LibreriaMusica y adentro 
#/Yeah Yeah Yeahs/Fever/maps.mp3 (u otra canción, pero deberá cambiarse el nombre del archivo en el cliente)
class ServidorStreaming
    #Aquí utilizo un buffer de 4096
    $BUFFER_SIZE = 4096
    DIRECCION = "127.0.0.1"
    PUERTO = 1234
    $servidor = UDPSocket.new
    $servidor.bind(DIRECCION, PUERTO)
    puts "Servidor en espera de clientes"
    def ServidorStreaming.recuperarCancion(ruta)
        rutaInicio = Dir.home
        rutaInicio = rutaInicio + "/LibreriaMusica/"
        pathRuta = Pathname(rutaInicio + ruta)
        puts pathRuta
        if pathRuta.exist?
            puts "La encontró"
            archivo = File.open(pathRuta, "r")
            return archivo
        end
    end
    def self.enviarCancion(archivo, remitente)
        while chunk = archivo.read($BUFFER_SIZE)
            $servidor.send(chunk, 0, remitente[2], remitente[1])
        end
        archivo.close
    end

    loop do
        puts "inicio"
        ruta, remitente = $servidor.recvfrom($BUFFER_SIZE)
        puts "Cliente conectado"
        archivo = ServidorStreaming.recuperarCancion(ruta)
        puts "Size #{archivo.size}"
        ServidorStreaming.enviarCancion(archivo, remitente)

    end
end

