/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.x10cm15.internal;

import org.openhab.binding.x10cm15.X10cm15BindingProvider;
import org.openhab.core.binding.BindingConfig;
import org.openhab.core.items.Item;
import org.openhab.core.library.items.DimmerItem;
import org.openhab.core.library.items.RollershutterItem;
import org.openhab.core.library.items.SwitchItem;
import org.openhab.model.item.binding.AbstractGenericBindingProvider;
import org.openhab.model.item.binding.BindingConfigParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class X10cm15GenericBindingProvider extends AbstractGenericBindingProvider
        implements X10cm15BindingProvider {

    static final Logger logger = LoggerFactory.getLogger(X10cm15GenericBindingProvider.class);

    /**
     * Este método devuelve el tipo del binding
     *
     * @return tipo X10Binding
     */
    @Override
    public String getBindingType() {
        return "x10cm15";
    }

    /**
     * En este método se comprueba que el objeto creado en e fichero item sea del tipo Switch, Dimmer o
     * RollershutterItem
     *
     * @param item es el objeto creado virtualmente
     * @param bindingConfig es la configuración del objeto
     */
    @Override
    public void validateItemType(Item item, String bindingConfig) throws BindingConfigParseException {
        if (!(item instanceof SwitchItem) && !(item instanceof DimmerItem || item instanceof RollershutterItem)) {
            throw new BindingConfigParseException("item '" + item.getName() + "' is of type '"
                    + item.getClass().getSimpleName()
                    + "', only SwitchItems and DimmerItem are allowed - please check your *.items configuration");
        }
    }

    /**
     * Se crean objetos dependiendo del tipo de elemento.
     *
     * @param context
     * @param item es el objeto creado virtualmente
     * @param bindingConfig es la configuracion del objeto
     */
    @Override
    public void processBindingConfiguration(String context, Item item, String bindingConfig)
            throws BindingConfigParseException {
        super.processBindingConfiguration(context, item, bindingConfig);

        try {
            if (bindingConfig != null) {

                String configParts = bindingConfig;
                String itemType = item.toString();

                if (item instanceof SwitchItem) {

                    BindingConfig x10cm15BindingConfig = new x10cm15BindingConfig(configParts, itemType);
                    addBindingConfig(item, x10cm15BindingConfig);

                } else if (item instanceof DimmerItem) {

                    BindingConfig x10cm15BindingConfig = new x10cm15BindingConfig(configParts, itemType);
                    addBindingConfig(item, x10cm15BindingConfig);

                }
            } else {
                logger.warn("bindingConfig is NULL (item=" + item + ") -> processing bindingConfig aborted!");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.warn("bindingConfig is invalid (item=" + item + ") -> processing bindingConfig aborted!");
        }
    }

    /**
     * Devuelve la configuraicon del objeto
     * 
     * @param itemName direccion del dispositivo
     */
    @Override
    public x10cm15BindingConfig getItemConfig(String itemName) {
        return (x10cm15BindingConfig) bindingConfigs.get(itemName);
    }

}
