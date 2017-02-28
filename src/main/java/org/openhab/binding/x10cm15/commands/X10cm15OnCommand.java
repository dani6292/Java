/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.openhab.binding.x10cm15.commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import org.openhab.binding.x10cm15.internal.X10cm15Activator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class X10cm15OnCommand implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(x10cm15Activator.class);
    private String address;

    public X10cm15OnCommand() {
        this.address = "No se ha recibido ninguna dirección";
    }

    public X10cm15OnCommand(String address) {
        this.address = address;
        logger.debug("address vale'" + address + "'");
    }

    /* añadido nuevo */

    /* añadido nuevo */

    @Override
    public void run() {
        logger.debug("entra en el run");
        try {
            Process p = Runtime.getRuntime().exec("cm15a"); // ejecuta el programa en c

            OutputStream os = p.getOutputStream();
            PrintStream ps = new PrintStream(os); // Conecta la entrada del programa en c con la salida de java
            logger.debug("Si se ejecuta el cm15a");
            ps.println('x'); // introduce el caracter x para que la libreria reciba un comando
            ps.flush(); // debido a que pulsamos un intro al introducir un caracter
            ps.println(this.address + " on"); // ej en el caso de que queramos encender el módulo.
            ps.flush(); // debido a que pulsamos un intro al introducir un caracter
            ps.println("q"); // ej en el caso de que queramos encender el módulo.
            ps.flush(); // debido a que pulsamos un intro al introducir un caracter
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("NO se ejecuta el cm15a");
        }

    }
}