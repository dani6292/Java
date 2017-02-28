/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.x10cm15.commands;

/**
 * This class is to turn off a X10 device
 *
 * @author Daniel Niño
 * @since 1.9.0
 */

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class X10cm15OffCommand implements Runnable {
    private String address;

    public X10cm15OffCommand() {
        this.address = "No se ha recibido ninguna dirección";
    }

    public X10cm15OffCommand(String address) {
        this.address = address;
    }

    @Override
    public void run() {
        try {

            Process p = Runtime.getRuntime().exec("cm15a"); // ejecuta el programa en c

            OutputStream os = p.getOutputStream();
            PrintStream ps = new PrintStream(os); // Conecta la entrada del programa en c con la salida de java

            ps.println('x'); // introduce el caracter x para que la libreria reciba un comando
            ps.flush(); // debido a que pulsamos un intro al introducir un caracter
            ps.println(this.address + " off"); // ej en el caso de que queramos encender el módulo.
            ps.flush(); // debido a que pulsamos un intro al introducir un caracter
            ps.println("q"); // ej en el caso de que queramos encender el módulo.
            ps.flush(); // debido a que pulsamos un intro al introducir un caracter
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}