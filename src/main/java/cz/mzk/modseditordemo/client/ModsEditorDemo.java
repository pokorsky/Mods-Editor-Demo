/*
 * Copyright (C) 2012 Jan Pokorsky
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.mzk.modseditordemo.client;

import com.google.gwt.core.client.EntryPoint;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point classes define
 * <code>onModuleLoad()</code>.
 */
public class ModsEditorDemo implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        VLayout rootPanel = new VLayout();
        rootPanel.setHeight100();
        rootPanel.setWidth100();
        rootPanel.setContents("SmartGWT is ready!");

        rootPanel.draw();
    }
}
