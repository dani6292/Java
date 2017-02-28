/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.x10cm15.internal;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import org.openhab.binding.x10cm15.X10cm15BindingProvider;
import org.openhab.binding.x10cm15.commands.X10cm15AlloffCommand;
import org.openhab.binding.x10cm15.commands.X10cm15AllonCommand;
import org.openhab.binding.x10cm15.commands.X10cm15BriCommand;
import org.openhab.binding.x10cm15.commands.X10cm15DimCommand;
import org.openhab.binding.x10cm15.commands.X10cm15OffCommand;
import org.openhab.binding.x10cm15.commands.X10cm15OnCommand;
import org.openhab.binding.x10cm15.commands.X10cm15PercentCommand;
import org.openhab.core.binding.AbstractActiveBinding;
import org.openhab.core.library.types.IncreaseDecreaseType;
import org.openhab.core.library.types.OnOffType;
import org.openhab.core.library.types.PercentType;
import org.openhab.core.types.Command;
import org.openhab.core.types.State;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Daniel Ni�o 
 * @since 1.6.0
 */
public class X10cm15Binding extends AbstractActiveBinding<X10cm15BindingProvider> implements ManagedService {

    private static final Logger logger = LoggerFactory.getLogger(X10cm15Binding.class);
    private int level;
    /**
     * the refresh interval which is used to poll values from the x10cm15
     * server (optional, defaults to 60000ms)
     */
    private long refreshInterval = 60000;

    protected Map<String, DeviceConfig> deviceConfigs = new HashMap<String, DeviceConfig>();

    protected Map<String, String> pmsIpConfig = new HashMap<String, String>();

    protected Map<String, String> pmsPasswordConfig = new HashMap<String, String>();

    public x10cm15Binding() {
    }

    @Override
    public void activate() {
    }

    @Override
    public void deactivate() {
    }

    /**
     * @{inheritDoc}
     */
    @Override
    protected long getRefreshInterval() {
        return refreshInterval;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    protected String getName() {
        return "X10 Refresh Service";
    }

    /**
     * @{inheritDoc}
     */
    @Override
    protected void execute() {
        // the frequently executed code (polling) goes here ...
        logger.debug("execute() method is called!");
    }

    /**
     * Método principal de la clase.
     * Cada vez que se envia un comando al bus de eventos se recibe y se clasifica segun su funcionalidad en este método
     *
     * @param itemName Direccion del dispositivo que debe llevar a cabo la orden
     * @param command comando que debe llevarse a cabo
     */
    @Override
    protected void internalReceiveCommand(String itemName, Command command) {
        logger.debug("internalReceiveCommand() is called!");
        logger.debug("item vale'" + itemName + "'");

        x10cm15BindingConfig deviceConfig = getConfigForItemName(itemName);
        if (deviceConfig == null) {
            return;
        } else {

            if (itemName.equals("B1")) {

                if (OnOffType.ON.equals(command)) {
                    X10cm15AllonCommand llamadaAllOn = new X10cm15AllonCommand(itemName);
                    llamadaAllOn.run();
                    logger.debug("Encendiendo Todo");
                } else if (OnOffType.OFF.equals(command)) {
                    X10cm15AlloffCommand llamadaAllOff = new X10cm15AlloffCommand(itemName);
                    llamadaAllOff.run();
                    logger.debug("Apagando Todo");
                }
            } else {
                if (OnOffType.ON.equals(command)) {
                    X10cm15OnCommand llamadaOn = new X10cm15OnCommand(itemName);
                    llamadaOn.run();
                    logger.debug("Encendiendo");
                } else if (OnOffType.OFF.equals(command)) {
                    X10cm15OffCommand llamadaOff = new X10cm15OffCommand(itemName);
                    llamadaOff.run();
                    logger.debug("Apagado");

                } else if (IncreaseDecreaseType.INCREASE.equals(command)) {
                    X10cm15BriCommand llamadaBri = new X10cm15BriCommand(itemName);
                    llamadaBri.run();
                    logger.debug("Dimmer Increase");

                } else if (IncreaseDecreaseType.DECREASE.equals(command)) {
                    X10cm15DimCommand llamadaDim = new X10cm15DimCommand(itemName);
                    llamadaDim.run();
                    logger.debug("Dimmer Decrease");

                } else if (command instanceof PercentType) {

                    level = ((PercentType) command).intValue();

                    if (((PercentType) command).intValue() == 0) {
                        // If percent value equals 0 the x10 "off" command is used instead of the dim command
                        X10cm15OffCommand llamadaOff = new X10cm15OffCommand(itemName);
                        llamadaOff.run();
                        logger.debug("Apagado");

                    } else {
                        X10cm15PercentCommand llamadaPercent = new X10cm15PercentCommand(itemName, level);
                        llamadaPercent.run();
                        logger.debug("%");
                    }

                } else {

                    logger.debug("Nada");
                }
            }
        }
    }

    /**
     * @{inheritDoc}
     */
    @Override
    protected void internalReceiveUpdate(String itemName, State newState) {
        logger.debug("internalReceiveCommand() is called!");
    }

    /**
     * Lookup of the configuration of the named item.
     *
     * @param itemName
     *            The name of the item.
     * @return The configuration, null otherwise.
     */
    private x10cm15BindingConfig getConfigForItemName(String itemName) {
        for (x10cm15BindingProvider provider : this.providers) {
            if (provider.getItemConfig(itemName) != null) {
                return provider.getItemConfig(itemName);
            }
        }
        return null;
    }

    protected void addBindingProvider(x10cm15BindingProvider bindingProvider) {
        super.addBindingProvider(bindingProvider);
    }

    protected void removeBindingProvider(x10cm15BindingProvider bindingProvider) {
        super.removeBindingProvider(bindingProvider);
    }

    /**
     * Internal data structure which carries the connection details of one
     * device (there could be several)
     */
    static class DeviceConfig {

        String host;
        String password;
        String deviceId;

        public DeviceConfig(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getHost() {
            return host;
        }

        public String getPassword() {
            return password;
        }

        @Override
        public String toString() {
            return "Device [id=" + deviceId + "]";
        }
    }

    @Override
    public void updated(Dictionary<String, ?> properties) throws ConfigurationException {
        // TODO Auto-generated method stub

    }
}
