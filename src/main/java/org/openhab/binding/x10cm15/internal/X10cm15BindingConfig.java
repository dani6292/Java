/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.x10cm15.internal;

import java.util.HashMap;

import org.openhab.core.binding.BindingConfig;
import org.openhab.model.item.binding.BindingConfigParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for parsing the binding configuration.
 *
 * @author Daniel Niño
 * @since 1.6.0
 */

public class X10cm15BindingConfig extends HashMap<String, String> implements BindingConfig {
    /** generated serialVersion UID */
    private static final long serialVersionUID = -8702006872563774395L;

    static final Logger logger = LoggerFactory.getLogger(X10cm15BindingConfig.class);

    /**
     * The deviceId is X10 address.
     */
    private final String deviceId;

    /**
     * The itemType
     */
    private final String itemType;

    // private final ChannelTypeDef pwmChannel;

    public X10cm15BindingConfig(String deviceId, String itemType) throws BindingConfigParseException {
        this.deviceId = parseDeviceIdConfigString(deviceId);
        this.itemType = parseItemType(itemType);
    }

    /**
     * Analiza la cadena deviceId que se ha encontrado en la configuración.
     *
     * @param configString
     * 
     * 
     * @return deviceId es una cadena de caracteres.
     * @throws BindingConfigParseException
     */
    private String parseDeviceIdConfigString(String configString) throws BindingConfigParseException {
        try {
            return configString;
        } catch (Exception e) {
            throw new BindingConfigParseException("Error parsing deviceId.");
        }
    }

    private String parseItemType(String configString) throws BindingConfigParseException {

        if (configString != null) {
            try {
                return configString;
            } catch (Exception e) {
                throw new BindingConfigParseException("Error parsing item type.");
            }

        }
        return null;
    }

    /**
     * @return deviceId Devuelve el deviceId que ha sido declarado en el binding
     *         configuration.
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @return itemType Devuelve el tipo de objeto declarado en la configuracion del binding configuration.
     */
    public String getItemType() {
        return itemType;
    }

}
