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

public class X10cm15DimCommand implements Runnable {
    private String address;

    public X10cm15DimCommand() {
        this.address = "No se ha recibido ninguna dirección";
    }

    public X10cm15DimCommand(String address) {
        this.address = address;
    }

    @Override
    public void run() {
        Process p;
        try {
            p = Runtime.getRuntime().exec("cm15a"); // Run an external C program

            OutputStream os = p.getOutputStream();
            PrintStream ps = new PrintStream(os); // Connect the input of the c program with the output of the java
                                                  // program

            ps.println('x'); // We introduce a "x" to give a command to our c program
            ps.flush();
            ps.println(address + " dim 15%"); // CAMBIAR A DIMMER
            ps.flush(); // debido a que pulsamos un intro al introducir un caracter
            ps.println("q"); // ej en el caso de que queramos encender el módulo.
            ps.flush(); // debido a que pulsamos un intro al introducir un caracter

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}