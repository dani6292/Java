/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.x10cm15;

import org.openhab.binding.x10cm15.internal.X10cm15BindingConfig;
import org.openhab.core.binding.BindingProvider;

/**
 * @author Daniel Niño
 * @since 1.6.0
 */
public interface X10cm15BindingProvider extends BindingProvider {

    /**
     * Returns the configuration for the item with the given name.
     * 
     * @param itemName
     * @return the configuration if there is an item with the given name, null otherwise.
     */

    public X10cm15BindingConfig getItemConfig(String itemName);

}
