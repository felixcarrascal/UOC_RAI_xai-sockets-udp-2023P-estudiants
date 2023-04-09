/*

* Copyright (c) Joan-Manuel Marques 2013. All rights reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
*
* This file is part of the practical assignment of Distributed Systems course.
*
* This code is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This code is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this code.  If not, see <http://www.gnu.org/licenses/>.
*/

package udp.client;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uoc.dpcs.lsim.logger.LoggerManager.Level;
import lsim.library.api.LSimLogger;
import udp.servidor.RemoteMapUDPservidor;


/**
 * @author Joan-Manuel Marques
 *
 */

public class RemoteMapUDPclient {
	
	public static final int SOCKET_CLIENT = 6001;

	public RemoteMapUDPclient() {
	}
	
	public Map<String, String> getMap (List<Key> keys) {
		Map<String, String> map = new HashMap<String, String>();
		int i = 1;
		for (Key key : keys) {
			LSimLogger.log(
					Level.TRACE,
					"["+i+"] Query for key "+key.getKey()+" at "+ key.getServerAddress() +":"+key.getServerPort()
					);

			String value = get(key.getKey(), key.getServerAddress(), key.getServerPort());

			LSimLogger.log(Level.TRACE, "["+i+"] RemoteMap("+key.getKey()+"): "+ value);
			i++;
			map.put(key.getKey(), value);
		}

		return map;
	}
	
	private String get(String key, String server_address, int server_port){
		LSimLogger.log(Level.INFO, "inici RemoteMapUDPclient.get ");
		LSimLogger.log(Level.TRACE, "key: " + key);
		LSimLogger.log(Level.TRACE, "server_address: " + server_address);
		LSimLogger.log(Level.TRACE, "server_port: " + server_port);

		
		
		byte[] mensaje_bytes = new byte[4];
		DatagramPacket paquete;
		boolean recibido = false;
		String resposta = null;
		try (DatagramSocket socket = new DatagramSocket(SOCKET_CLIENT)) {
			
			InetAddress adr = InetAddress.getByName("localhost");
			
			System.arraycopy(key.getBytes(), 0, mensaje_bytes, 0, key.getBytes().length);
			
			paquete = new DatagramPacket(mensaje_bytes, mensaje_bytes.length, adr, RemoteMapUDPservidor.SOCKET_SERVER);
			socket.send(paquete);
			
			while(!recibido) {
				DatagramPacket request = new DatagramPacket(mensaje_bytes, mensaje_bytes.length);
				socket.receive(request);
				resposta = new String(request.getData());
				LSimLogger.log(Level.INFO, "Cliente recibe: " + resposta);
				if(!resposta.isEmpty() && !resposta.isBlank()) {
					recibido = true;
				}
			}
			
		} catch (IOException ex) {
			System.err.println(ex);
		}
		
		
		
		
		/* TODO: implementació de la part client UDP / implement UDP client's side / implementación de la parte cliente UDP */
		
		
		
		
		return resposta;
	}
}
